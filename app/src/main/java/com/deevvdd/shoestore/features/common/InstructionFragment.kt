package com.deevvdd.shoestore.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentInstructionBinding

class InstructionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentInstructionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_instruction, container, false)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(InstructionFragmentDirections.actionInstructionFragmentToAppGraph())
        }
        return binding.root
    }
}