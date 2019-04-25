package com.labsidea.blumenausocial.ui.institution.filter

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import com.labsidea.blumenausocial.R

class InstitutionsFiltersAdapter(private val context: Context, private val listHeaders: List<String>, private val listItems: HashMap<String, List<String>>): BaseExpandableListAdapter(){


    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflate.inflate(R.layout.item_header_institutions_filters_adapter, null)



        val title = view.findViewById(R.id.tvTitle) as TextView
        val ivExpand = view.findViewById(R.id.ivExpand) as ImageView

        title.text = listHeaders[groupPosition]

        ivExpand.setOnClickListener {


            //events?.onSelectedAll(groupPosition)

            this@InstitutionsFiltersAdapter.onGroupExpanded(groupPosition)
        }



        return view
    }




    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflate.inflate(R.layout.item_institutions_filters_adapter, null)



        val rvInstitutions    = view.findViewById(R.id.rvInstitutions) as RecyclerView


        //title.text    = getChild(groupPosition, childPosition)!![childPosition].fantasy_name

        //if (title.text.isEmpty())
        //getChild(groupPosition, childPosition)!![childPosition].class_name


        rvInstitutions.adapter = FilterAdapter(context, getChild(groupPosition, childPosition)!!)
        rvInstitutions.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

        //tvTitle.text = getChild(groupPosition, childPosition)!![childPosition]

        return view
    }


    override fun getGroup(groupPosition: Int) = listHeaders[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = listItems[getGroup(groupPosition)]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun getGroupCount() = listHeaders.size

    override fun getChildrenCount(groupPosition: Int) = 1//listItems[listHeaders[groupPosition]]!!.size

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    override fun hasStableIds() = false

}