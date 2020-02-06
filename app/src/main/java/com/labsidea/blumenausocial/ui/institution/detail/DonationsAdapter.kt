/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import kotlinx.android.synthetic.main.item_donation.view.*

class DonationsAdapter(val list: List<String>): RecyclerView.Adapter<DonationsAdapter.DonationsAdapterViewHolder>() {
    inner class DonationsAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(title: String){
            itemView.btnVolunteers.text = title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DonationsAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_donation, parent, false))

    override fun onBindViewHolder(holder: DonationsAdapterViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size
}