/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.filter

import android.content.Context
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.ItemAdapter
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val context: Context, val list: List<ItemAdapter>, val onClick: (item: ItemAdapter) -> Unit): RecyclerView.Adapter<FilterAdapter.FilterAdapterViewHolder>() {

    inner class FilterAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item: ItemAdapter){

            if (!item.icon.isNullOrEmpty()) {
                val imageLogo = context.resources.getIdentifier(item.icon, "drawable", context.packageName)
                Glide.with(context)
                        .load(imageLogo)
                        .centerInside()
                        .placeholder(R.drawable.anim_loader)
                        .error(R.drawable.no_address)
                        .into(itemView.ivLogo)
            }
            else {
                itemView.container.layoutParams.height = 110//60//LinearLayout.LayoutParams.WRAP_CONTENT
                itemView.ivLogo.visibility = View.GONE
            }

            if (item.selected == 1){ //in current filter.
                itemView.container.setBackgroundColor(getColor(context, R.color.colorBlueSelected))
                itemView.container.isSelected = true
            }

            itemView.container.setOnClickListener {
                itemView.container.isSelected = !itemView.container.isSelected
                itemView.container.setBackgroundColor(if (itemView.container.isSelected)
                                                         getColor(context, R.color.colorBlueSelected)
                                                      else
                                                         getColor(context, android.R.color.white))

                item.selected = itemView.container.isSelected.toInt()
                onClick(item)
            }

            itemView.tvTitle.text = item.description
        }
    }

    fun Boolean.toInt() = if (this) 1 else 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FilterAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false))

    override fun onBindViewHolder(holder: FilterAdapterViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size
}