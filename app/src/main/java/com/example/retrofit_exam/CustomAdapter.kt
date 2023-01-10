import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofit_exam.Repository
import com.example.retrofit_exam.RepositoryItem
import com.example.retrofit_exam.databinding.GitDataViewBinding

class CustomAdapter : RecyclerView.Adapter<Holder>() {
    var userList : Repository? = null //자동으로 생성된 repository(arrayList)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
    val binding = GitDataViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        //레이아웃을 인플레이트 한 후 바인딩에 담아서 반환한다.
        return  Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }

    override fun getItemCount(): Int {
    return userList?.size?: 0
    }
}

class Holder(val binding : GitDataViewBinding):RecyclerView.ViewHolder(binding.root){
    fun setUser(user : RepositoryItem?){
        user?.let{
            binding.textName.text = it.name
            binding.textId.text = it.node_id
            Glide.with(binding.imageView).load(it.owner.avatar_url).into(binding.imageView)
        }
    }
}