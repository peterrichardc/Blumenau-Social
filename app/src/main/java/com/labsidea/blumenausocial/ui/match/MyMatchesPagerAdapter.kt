package com.labsidea.blumenausocial.ui.match

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.Organization
import com.labsidea.blumenausocial.ui.institution.detail.DonationsAdapter
import kotlinx.android.synthetic.main.item_my_matches.view.*


class MyMatchesPagerAdapter(val context: Context, val institutions: List<Organization>, private val onClickItem: (institutionID: Int) -> Unit): PagerAdapter(){

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_my_matches, container, false) as ViewGroup
        val mainContainer = itemView.findViewById<LinearLayout>(R.id.main_container)
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvPhone = itemView.findViewById<TextView>(R.id.tvPhone)
        val tvAddress = itemView.findViewById<TextView>(R.id.tvAddress)
        val tvMail = itemView.findViewById<TextView>(R.id.tvMail)
        val imageLogo = context.resources.getIdentifier(institutions[position].logo,  "drawable", context.packageName)
        Glide.with(context)
                .load(imageLogo)
                .centerInside()
                .placeholder(R.drawable.anim_loader)
                .error(R.drawable.ic_cvv)
                .into(itemView.ivLogo)

        tvName?.text = institutions[position].title
        tvDescription?.text = institutions[position].subtitle
        tvPhone?.text = institutions[position].phone
        tvAddress?.text = institutions[position].address
        tvMail?.text = institutions[position].mail

        mainContainer.setOnClickListener { onClickItem(institutions[position].id) }
        container.addView(itemView)
        return itemView
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)

//        super.destroyItem(container, position, view)
    }

    override fun isViewFromObject(view: View, `object`: Any) = view === `object`

    override fun getCount() = institutions.size

}