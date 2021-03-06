package com.raion.furnitale.ui.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.viewbinding.library.fragment.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.raion.furnitale.R
import com.raion.furnitale.databinding.AccountFragmentBinding
import com.raion.furnitale.ui.SignInActivity

class AccountFragment : Fragment() {

    private val accountBinding: AccountFragmentBinding by viewBinding()

    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.account_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }!!

        val account = GoogleSignIn.getLastSignedInAccount(activity)

        if (account != null) {
            val personName = account.displayName
            val personEmail = account.email

            accountBinding.includeAccountDetail.apply {
                tvProfileName.text = personName
                tvMemberSince.text = personEmail
            }

            val avatar = account.photoUrl
            accountBinding.let {
                Glide.with(this)
                    .load(avatar)
                    .apply(RequestOptions.circleCropTransform())
                    .into(accountBinding.includeAccountDetail.ivProfile)
            }
        }

        accountBinding.includeAccountSetting.btnLogout.setOnClickListener {
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        mGoogleSignInClient.signOut().addOnCompleteListener {
            val intent = Intent(activity, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }
}