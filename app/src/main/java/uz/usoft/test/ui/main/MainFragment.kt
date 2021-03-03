package uz.usoft.test.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import uz.usoft.test.R
import uz.usoft.test.core.extention.onClick
import uz.usoft.test.databinding.FragmentMainBinding
import uz.usoft.test.databinding.FragmentSearchBinding

class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var navController: NavController
    private lateinit var binding: FragmentMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding = FragmentMainBinding.bind(view)
        binding.btn1.onClick {
            val action = MainFragmentDirections.actionMainFragmentToSearchVideoFragment()
            navController.navigate(action)
        }
    }
}