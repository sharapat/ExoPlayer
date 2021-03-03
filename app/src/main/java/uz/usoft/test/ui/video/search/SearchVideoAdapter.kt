package uz.usoft.test.ui.video.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.usoft.test.R
import uz.usoft.test.core.extention.inflate
import uz.usoft.test.core.extention.onClick
import uz.usoft.test.data.model.Video
import uz.usoft.test.databinding.ItemVideoBinding

class SearchVideoAdapter : RecyclerView.Adapter<SearchVideoAdapter.SearchVideoViewHolder>() {

    var data: List<Video> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClick: (url: String) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (url: String) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class SearchVideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(videoItem: Video) {
            binding.apply {
                Glide.with(binding.root)
                    .load(videoItem.thumb)
                    .into(videoImage)
                tvTitle.text = videoItem.title
                root.onClick {
                    onItemClick.invoke(videoItem.sources[0])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchVideoViewHolder {
        val itemView = parent.inflate(R.layout.item_video)
        val binding = ItemVideoBinding.bind(itemView)
        return SearchVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchVideoViewHolder, position: Int) {
        holder.populateModel(data[position])
    }

    override fun getItemCount() = data.size
}