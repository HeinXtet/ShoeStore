package com.deevvdd.shoestore.features.login

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentSplashBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashFragment : Fragment() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)

        auth = Firebase.auth

        val currentUser = auth.currentUser


        binding.tvSplash.animate().setDuration(2000).alpha(1f).scaleX(1f).scaleY(1f)
            .setInterpolator(BounceInterpolator())
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (currentUser !== null) {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToAppGraph2())
                        } else {
                            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                        }
                    }, 1000)
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }
            }).start()
        return binding.root
    }
}