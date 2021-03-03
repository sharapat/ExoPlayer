package uz.usoft.test.ui.video.search

import uz.usoft.test.core.Resource
import uz.usoft.test.data.model.Item

interface SearchVideoView {
    fun render(model: Resource<List<Item>>)
}