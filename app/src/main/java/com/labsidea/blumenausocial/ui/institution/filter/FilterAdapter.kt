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
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.ItemAdapter
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val context: Context, val list: List<ItemAdapter>, val onClick: (item: ItemAdapter) -> Unit): RecyclerView.Adapter<FilterAdapter.FilterAdapterViewHolder>() {

    inner class FilterAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        fun bind(item: ItemAdapter){
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
                onClick(item)
            }

            itemView.tvTitle.text = item.description
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterAdapterViewHolder {
        val inflate: LayoutInflater = LayoutInflater.from(parent.context);
        val view: View = inflate.inflate(R.layout.item_filter, parent, false)

        return FilterAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilterAdapterViewHolder, position: Int) {
        holder.bind(list[position])

    }


    override fun getItemCount() = list.size
}