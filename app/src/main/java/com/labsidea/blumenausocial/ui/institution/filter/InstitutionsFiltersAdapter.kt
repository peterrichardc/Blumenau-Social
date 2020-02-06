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
import com.labsidea.blumenausocial.models.InstitutionsFilterHeader
import com.labsidea.blumenausocial.models.ItemAdapter
import com.labsidea.blumenausocial.models.ItemsSelected

class InstitutionsFiltersAdapter(private val context: Context,
                                 private val listHeaders: List<InstitutionsFilterHeader>,
                                 private val listItems: HashMap<InstitutionsFilterHeader, List<ItemAdapter>>,
                                 val listFilters: MutableList<ItemsSelected>,
                                 private val events: InstitutionsFiltersAdapterEvents?): BaseExpandableListAdapter(){


    interface InstitutionsFiltersAdapterEvents{
        fun onClickExpandOrCollapse(groupPosition: Int)
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflate.inflate(R.layout.item_header_institutions_filters_adapter, null)

        val title = view.findViewById(R.id.tvTitle) as TextView
        val ivExpand = view.findViewById(R.id.ivExpand) as ImageView
        val subTitle = view.findViewById(R.id.tvSubTitle) as TextView

        title.text = listHeaders[groupPosition].title
        subTitle.text = listHeaders[groupPosition].subtitle

        val ivLogo = view.findViewById(R.id.ivLogo) as ImageView
        ivLogo.setImageResource(listHeaders[groupPosition].resourceId)

        ivExpand.setOnClickListener {
            events?.onClickExpandOrCollapse(groupPosition)

            this@InstitutionsFiltersAdapter.onGroupExpanded(groupPosition)
        }

        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflate.inflate(R.layout.item_institutions_filters_adapter, null)

        val rvInstitutions = view.findViewById(R.id.rvInstitutions) as RecyclerView

        rvInstitutions.adapter = FilterAdapter(context, getChild(groupPosition, childPosition)!!) { item ->
            //Selected Record or not..

            //Find exists in list or not..
            val add = listFilters.none { it.id == item.id && it.type == getGroup(groupPosition).type }
            if (add)
                listFilters.add(ItemsSelected(item.id, getGroup(groupPosition).type))
            else
                listFilters.removeAll{ it.id == item.id && it.type == getGroup(groupPosition).type }

            //Update item in list of children.
            val position = getChild(groupPosition, childPosition)!!.indexOf(item)
            getChild(groupPosition, childPosition)!![position].selected = if (add) 1 else 0
        }
        rvInstitutions.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)

        return view
    }

    override fun getGroup(groupPosition: Int) = listHeaders[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = listItems[listHeaders[groupPosition]]

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun getGroupCount() = listHeaders.size

    override fun getChildrenCount(groupPosition: Int) = 1

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    override fun hasStableIds() = false

}
