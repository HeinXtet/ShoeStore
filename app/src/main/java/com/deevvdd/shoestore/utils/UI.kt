package com.deevvdd.shoestore.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.deevvdd.shoestore.R
import com.deevvdd.shoestore.model.ShoeModel
import com.google.android.material.card.MaterialCardView

object UI {
    private fun dp(value: Float, context: Context): Int {
        val r: Resources = context.getResources()
        val px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            r.displayMetrics
        ).toInt()
        return px
    }

    private fun createTv( title: String, context: Context): TextView {
        val titleTv = TextView(context)
        titleTv.setTextColor(Color.BLACK)
        val titlePaddingHorizontal = dp(8f,context)
        val titlePaddingVertical = dp(4f,context)
        titleTv.setPadding(
            titlePaddingHorizontal,
            titlePaddingVertical,
            titlePaddingHorizontal,
            titlePaddingVertical
        )
        val typeface = ResourcesCompat.getFont(context, R.font.roboto)
        titleTv.typeface = typeface
        titleTv.text = title
        return titleTv
    }

     fun generateShoeCard(
        containerView : LinearLayout,
        shoe: ShoeModel,
        isLastIndex: Boolean,
        context: Context
    ) {
        val cardLayoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            dp(140f, context)
        )
        val px = dp(16f, context)
        if (isLastIndex) {
            cardLayoutParams.setMargins(px, px, px, px)
        } else {
            cardLayoutParams.setMargins(px, px, px, 0)
        }
        val shoeCard = MaterialCardView(context)
        shoeCard.layoutParams = cardLayoutParams
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.HORIZONTAL
        val image = ImageView(context)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        val imageSize = context.resources.getDimensionPixelOffset(R.dimen.img_size)
        val lp = ViewGroup.LayoutParams(imageSize, ViewGroup.LayoutParams.MATCH_PARENT)
        image.layoutParams = lp
        Glide.with(context).load(shoe.imageUrl).into(image)
        linearLayout.addView(image)
        val contentLinearLayout = LinearLayout(context)
        contentLinearLayout.orientation = LinearLayout.VERTICAL


        contentLinearLayout.addView(createTv("Name : ${shoe.title}", context))
        contentLinearLayout.addView(createTv("Prize : $${shoe.prize}", context))
        contentLinearLayout.addView(createTv("Company : ${shoe.company}", context))
        contentLinearLayout.addView(createTv("Stock : ${shoe.quantity}", context))
        linearLayout.addView(contentLinearLayout)
        shoeCard.addView(linearLayout)
        containerView.addView(shoeCard)
    }
}