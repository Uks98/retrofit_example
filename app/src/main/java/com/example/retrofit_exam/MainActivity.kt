package com.example.retrofit_exam

import CustomAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofit_exam.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
class MainActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = CustomAdapter()
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager = LinearLayoutManager(this) //리니어 레이아웃 매니저 연결

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com") //도메인주소
            .addConverterFactory(GsonConverterFactory.create())
            .build() //repository 클래스의 컬렉션으로 변환해주는 컨버터 입력하고 build()메서드를 호출해 생성
        binding.buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)
            githubService.users().enqueue(object : Callback<Repository> {
                //서버로 부터 응답 받을 시 enqueue안에 작성하는 콜백 인터페이스 작동
                override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                    //응답 성공했을 시
                    adapter.userList = response.body() as Repository
                    adapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<Repository>, t: Throwable) {
                }
            }) //users메서드 안에 비동기 통신으로 데이터를 가져오는 enqueue 메서드 추가
        }
    }
}


// 호출방식 주소 데이터등을 지정한다.
interface GithubService{
    @GET("users/Kotlin/repos") //요청주소 설정 도메인은 제외하고 작성한다.
    fun users(): Call<Repository>
}