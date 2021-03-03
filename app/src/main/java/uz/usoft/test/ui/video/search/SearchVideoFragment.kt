package uz.usoft.test.ui.video.search

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.usoft.test.App
import uz.usoft.test.R
import uz.usoft.test.core.BaseFragment
import uz.usoft.test.core.Resource
import uz.usoft.test.core.ResourceState
import uz.usoft.test.core.extention.addVertDivider
import uz.usoft.test.core.extention.onClick
import uz.usoft.test.core.extention.visibility
import uz.usoft.test.data.model.Item
import uz.usoft.test.databinding.FragmentSearchBinding
import javax.inject.Inject

class SearchVideoFragment : BaseFragment(R.layout.fragment_search), SearchVideoView {
    @Inject
    lateinit var presenter: SearchVideoPresenter
    private lateinit var binding: FragmentSearchBinding
    private lateinit var navController: NavController
    private val adapter = SearchVideoAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as App).component.inject(this)
        navController = Navigation.findNavController(view)
        binding = FragmentSearchBinding.bind(view)
            .apply {
                rvResult.addVertDivider(requireContext())
                rvResult.adapter = adapter
                searchBtn.onClick {
                    if (!etSearch.text.isNullOrEmpty()) {
                        presenter.searchVideo(etSearch.text.toString())
                    }
                }
            }
        adapter.setOnItemClickListener {
            val action = SearchVideoFragmentDirections.actionSearchVideoFragmentToWatchFragment(it)
            navController.navigate(action)
        }
        presenter.init(this)
        presenter.searchVideo("")
    }

    override fun render(model: Resource<List<Item>>) {
        when(model.status) {
            ResourceState.LOADING -> setLoading(true)
            ResourceState.SUCCESS -> {
                adapter.data = model.data!!
                setLoading(false)
            }
            ResourceState.ERROR -> {
                toastLN(model.message)
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.apply {
            progressBar.visibility(isLoading)
            etSearch.isEnabled = !isLoading
            rvResult.isEnabled = !isLoading
        }
    }
}