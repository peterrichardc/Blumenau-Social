/*
 * Author: Peter Richard Carlim.
 * github: peterrichardc .
 * e-mail: peter.richardcarlim@gmail.com .
*/

package com.labsidea.blumenausocial.ui.institution

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labsidea.blumenausocial.R
import com.labsidea.blumenausocial.models.Organization
import kotlinx.android.synthetic.main.item_organization.view.*

class InstitutionAdapter(private val context: Context, var list: List<Organization>, private val events: InstitutionAdapterEvents): RecyclerView.Adapter<InstitutionAdapter.InstitutionAdapterViewHolder>() {

    interface InstitutionAdapterEvents{
        fun onClickItem(institution: Organization)
    }

    inner class InstitutionAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){


        fun bind(institution: Organization){
            itemView.tvName.text = institution.title
            itemView.tvPhone.text = institution.phone
            itemView.tvAddress.text = institution.address

            itemView.main_container.setOnClickListener {
                events.onClickItem(institution)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InstitutionAdapterViewHolder {
        val inflate: LayoutInflater = LayoutInflater.from(parent.context);
        val view: View = inflate.inflate(R.layout.item_organization, parent, false)

        return InstitutionAdapterViewHolder(view)
    }

    override fun onBindViewHolder(holder: InstitutionAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount() = list.size
}