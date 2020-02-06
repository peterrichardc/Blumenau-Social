/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.Organization
import kotlinx.android.synthetic.main.item_organization.view.*

class InstitutionsAdapter(var list: List<Organization?>,
                          private val onClickItem: (institution: Organization?) -> Unit): RecyclerView.Adapter<InstitutionsAdapter.InstitutionAdapterViewHolder>() {

    inner class InstitutionAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(institution: Organization?){
            itemView.tvName.text = institution?.title
            itemView.tvPhone.text = institution?.phone
            itemView.tvAddress.text = institution?.address

            itemView.main_container.setOnClickListener {
                onClickItem(institution)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = InstitutionAdapterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_organization, parent, false))

    override fun onBindViewHolder(holder: InstitutionAdapterViewHolder, position: Int) = holder.bind(list[position])

    override fun getItemCount() = list.size
}