package com.deevvdd.shoestore.features

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentAddShoeBinding
import com.deevvdd.shoestore.features.main.MainViewModel
import timber.log.Timber

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
        initUI()
        return binding.root
    }


    private fun initUI() {
        mainViewModel.isValidToCreate.observe(viewLifecycleOwner, { isValid ->
            binding.btnCreate.isEnabled = isValid
        })
        with(binding) {
            btnCreate.setOnClickListener {
                mainViewModel.addNewShoe()
                findNavController().popBackStack(R.id.addNewShoeFragment, true)
            }
            btnCancel.setOnClickListener { findNavController().popBackStack() }
            edName.addTextChangedListener { mainViewModel.updateShoeName(it.toString()) }
            edCompany.addTextChangedListener { mainViewModel.updateCompany(it.toString()) }
            edPrize.addTextChangedListener { mainViewModel.updatePrize(it.toString()) }
            edStock.addTextChangedListener { mainViewModel.updateStock(it.toString()) }
        }
    }
}