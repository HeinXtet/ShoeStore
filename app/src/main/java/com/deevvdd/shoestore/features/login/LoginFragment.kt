package com.deevvdd.shoestore.features.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentLoginBinding
import com.deevvdd.shoestore.utils.hide
import com.deevvdd.shoestore.utils.hideKeyboard
import com.deevvdd.shoestore.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        actionChanged()
        initObserver()
        return binding.root
    }

    private fun actionChanged() {
        with(binding) {
            edEmail.addTextChangedListener { loginViewModel.updateEmail(it?.trim().toString()) }
            edPassword.addTextChangedListener {
                loginViewModel.updatePassword(
                    it?.trim().toString()
                )
            }
            btnSignIn.setOnClickListener {
                loginViewModel.signIn()
                view?.hideKeyboard()
            }
            btnSignUp.setOnClickListener {
                loginViewModel.signUp()
                view?.hideKeyboard()
            }
        }
    }


    private fun initObserver() {
        loginViewModel.isValidToAuthenticate.observe(viewLifecycleOwner, {
            binding.btnSignIn.isEnabled = it
            binding.btnSignUp.isEnabled = it
        })

        loginViewModel.loading.observe(viewLifecycleOwner, { loading ->
            if (loading) binding.pgAuth.show() else binding.pgAuth.hide()
        })

        loginViewModel.userData.observe(viewLifecycleOwner, { user ->
            if (user != null) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToWelcomeFragment())
            }
        })

        loginViewModel.error.observe(viewLifecycleOwner, { error ->
            if (error != null) {
                MaterialDialog(requireContext()).show {
                    title(R.string.error_title)
                    message(text = error)
                    positiveButton(R.string.btn_text_close)
                }
            }
        })
    }
}