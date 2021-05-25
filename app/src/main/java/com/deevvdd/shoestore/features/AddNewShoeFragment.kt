package com.deevvdd.shoestore.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentAddShoeBinding
import com.deevvdd.shoestore.features.main.MainViewModel

class AddNewShoeFragment : Fragment() {
    lateinit var binding: FragmentAddShoeBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_shoe, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = mainViewModel
        mainViewModel.shoeCreated.observe(viewLifecycleOwner, { created ->
            if (created) {
                mainViewModel.updateShoeCreated()
                findNavController().popBackStack()
            }
        })
        return binding.root
    }
}