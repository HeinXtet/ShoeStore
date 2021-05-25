package com.deevvdd.shoestore.features.home

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentShoeListingBinding
import com.deevvdd.shoestore.features.main.MainViewModel
import com.google.firebase.auth.FirebaseAuth

class ShoeListingFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: FragmentShoeListingBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_shoe_listing,
            container,
            false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = mainViewModel
        initUI()
        return binding.root
    }

    private fun initUI() {
        setHasOptionsMenu(true)
        with(binding) {
            fbNewShoe.setOnClickListener {
                findNavController().navigate(ShoeListingFragmentDirections.actionShoeListingFragmentToAddNewShoeFragment())
            }
        }
    }

    private fun handleOnLogout() {
        auth.signOut()
        findNavController().navigate(ShoeListingFragmentDirections.actionShoelistingFragmentToLogin())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.app_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLogout -> handleOnLogout()
        }
        return super.onOptionsItemSelected(item)
    }
}