package com.deevvdd.shoestore.features.home

import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.databinding.FragmentShoeListingBinding
import com.deevvdd.shoestore.features.main.MainViewModel
import com.deevvdd.shoestore.model.ShoeModel
import com.deevvdd.shoestore.utils.hide
import com.deevvdd.shoestore.utils.show
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


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
        auth = Firebase.auth
        initUI()
        initUser()
        initObserver()
        return binding.root
    }

    private fun initUI() {
        with(binding) {
            fbNewShoe.setOnClickListener {
                findNavController().navigate(ShoeListingFragmentDirections.actionShoeListingFragmentToAddNewShoeFragment())
            }
        }
    }

    private fun initUser() {
        setHasOptionsMenu(true)
        val currentUser = auth.currentUser
    }

    private fun dp(value: Float): Int {
        val r: Resources = requireActivity().getResources()
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            r.displayMetrics
        ).toInt()
        return px
    }

    private fun createTv(title: String): TextView {
        val titleTv = TextView(requireContext())
        titleTv.setTextColor(Color.BLACK)
        val titlePaddingHorizontal = dp(8f)
        val titlePaddingVertical = dp(4f)
        titleTv.setPadding(
            titlePaddingHorizontal,
            titlePaddingVertical,
            titlePaddingHorizontal,
            titlePaddingVertical
        )
        val typeface = ResourcesCompat.getFont(requireContext(), R.font.roboto)
        titleTv.typeface = typeface
        titleTv.text = title
        return titleTv
    }

    private fun generateShoeCard(shoe: ShoeModel, isLastIndex: Boolean) {
        val cardLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dp(140f)
        )
        val px = dp(16f)
        if (isLastIndex) {
            cardLayoutParams.setMargins(px, px, px, px)
        } else {
            cardLayoutParams.setMargins(px, px, px, 0)
        }
        val shoeCard = MaterialCardView(requireContext())
        shoeCard.layoutParams = cardLayoutParams
        val linearLayout = LinearLayout(requireContext())
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val image = ImageView(requireContext())
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        val imageSize = requireActivity().resources.getDimensionPixelOffset(R.dimen.img_size)
        val lp = ViewGroup.LayoutParams(imageSize, ViewGroup.LayoutParams.MATCH_PARENT)
        image.layoutParams = lp
        Glide.with(requireActivity()).load(shoe.imageUrl).into(image)
        linearLayout.addView(image)
        val contentLinearLayout = LinearLayout(requireContext())
        contentLinearLayout.orientation = LinearLayout.VERTICAL


        contentLinearLayout.addView(createTv(shoe.title ?: ""))
        contentLinearLayout.addView(createTv("Prize : $${shoe.prize}"))
        contentLinearLayout.addView(createTv("Company : ${shoe.company}"))
        contentLinearLayout.addView(createTv("Stock : ${shoe.quantity}"))
        linearLayout.addView(contentLinearLayout)
        shoeCard.addView(linearLayout)
        binding.shoeContainer.addView(shoeCard)
    }

    private fun initObserver() {
        mainViewModel.shoeLiveData.observe(viewLifecycleOwner, { shoes ->
            if (shoes.isNotEmpty()) {
                binding.tvEmpty.hide()
            } else {
                binding.tvEmpty.show()
            }
            binding.shoeContainer.removeAllViews()
            shoes.mapIndexed { index, shoeModel ->
                generateShoeCard(
                    shoeModel,
                    index == (shoes ?: ArrayList()).size - 1
                )
            }
        })
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