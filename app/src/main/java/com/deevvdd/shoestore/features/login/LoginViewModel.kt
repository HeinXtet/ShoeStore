package com.deevvdd.shoestore.features.login

import android.annotation.SuppressLint
import android.content.Context
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.utils.DoubleObserver
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @SuppressLint("StaticFieldLeak")
    @ApplicationContext val context: Context,
) : ViewModel() {


    private var auth: FirebaseAuth = Firebase.auth
    private val email = MutableLiveData("")
    private val password = MutableLiveData("")
    val loading = MutableLiveData(false)
    val userData = MutableLiveData<FirebaseUser>(null)
    val error = MutableLiveData<String>(null)


    private fun CharSequence?.isValidEmail() =
        !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private val authenticationLiveData = DoubleObserver(email, password)

    val isValidToAuthenticate =
        Transformations.map(authenticationLiveData) {
            it.first.isValidEmail() && it.second?.length ?: 0 >= 6 && !(loading.value ?: false)
        }


    fun updateEmail(text: String) {
        email.value = text
    }

    fun updatePassword(text: String) {
        password.value = text
    }

    fun signUp() {
        loading.value = true
        auth.createUserWithEmailAndPassword(email.value ?: "", password.value ?: "")
            .addOnCompleteListener { task ->
                loading.value = false
                val user = auth.currentUser
                if (task.isSuccessful) {
                    if (user != null) {
                        userData.value = auth.currentUser
                        Timber.d("createUserWithEmail:success ${user}")
                    } else {
                        error.value = context.getString(R.string.error_sign_up)
                    }

                } else {
                    error.value = task.exception?.localizedMessage
                    Timber.d("createUserWithEmail:failure ${task.exception}")
                }
            }
    }

    fun signIn() {
        loading.value = true
        auth.signInWithEmailAndPassword(email.value ?: "", password.value ?: "")
            .addOnCompleteListener { task ->
                loading.value = false
                val user = auth.currentUser
                if (task.isSuccessful) {
                    if (user != null) {
                        userData.value = auth.currentUser
                        Timber.d("createUserWithEmail:success ${user}")
                    } else {
                        error.value = context.getString(R.string.error_sign_in)
                    }
                    Timber.d("signInWithEmail:success ${user}")
                } else {
                    error.value = task.exception?.localizedMessage
                    Timber.d("signInWithEmail:failure ${task.exception}")
                }
            }
    }
}