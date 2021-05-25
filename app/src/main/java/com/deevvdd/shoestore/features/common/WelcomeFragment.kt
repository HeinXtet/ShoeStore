package com.deevvdd.shoestore.features.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentWelcomeBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class WelcomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentWelcomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_welcome, container, false)
        binding.btnNext.setOnClickListener {
            findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToInstructionFragment())
        }

        val currentUser = Firebase.auth.currentUser

        if (currentUser != null) {
            binding.lifecycleOwner = viewLifecycleOwner
            binding.user = currentUser
        }

        return binding.root
    }
}