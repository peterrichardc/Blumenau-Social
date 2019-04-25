/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.filter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.Organization
import kotlinx.android.synthetic.main.item_filter.view.*

class FilterAdapter(val context: Context, val list: List<String>): RecyclerView.Adapter<FilterAdapter.FilterAdapterViewHolder>() {


    inner class FilterAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun bind(title: String){
            //itemView.container.setOnClickListener { .}
            itemView.tvTitle.text = title
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