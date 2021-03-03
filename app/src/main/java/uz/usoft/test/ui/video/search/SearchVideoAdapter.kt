package uz.usoft.test.ui.video.search

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.usoft.test.R
import uz.usoft.test.core.extention.inflate
import uz.usoft.test.core.extention.onClick
import uz.usoft.test.data.model.Item
import uz.usoft.test.databinding.ItemVideoBinding

class SearchVideoAdapter : RecyclerView.Adapter<SearchVideoAdapter.SearchVideoViewHolder>() {

    var data: List<Item> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var onItemClick: (id: String) -> Unit = {}
    fun setOnItemClickListener(onItemClick: (id: String) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class SearchVideoViewHolder(private val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun populateModel(videoItem: Item) {
            binding.apply {
                Glide.with(binding.root)
                    .load(videoItem.snippet.thumbnails.high.url)
                    .into(videoImage)
                tvTitle.text = videoItem.snippet.title
                root.onClick {
                    onItemClick.invoke(videoItem.id.videoId)
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