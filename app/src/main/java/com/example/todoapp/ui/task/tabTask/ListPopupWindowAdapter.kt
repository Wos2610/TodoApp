package com.example.todoapp.ui.task.tabTask

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.lifecycle.LiveData
import com.example.todoapp.databinding.ItemCategoryBinding
import com.example.todoapp.databinding.ItemCategoryListPopupWindowBinding
import com.example.todoapp.model.Category
import com.example.todoapp.viewModel.CategoryViewModel


class ListPopupWindowAdapter(private val categories : LiveData<List<Category>>, activity: Activity?) : BaseAdapter() {
    private var mActivity: Activity? = null
    private var mDataSource: List<String> = ArrayList()
    private var layoutInflater: LayoutInflater? = null
//    private var clickDeleteButtonListener: OnClickDeleteButtonListener? = null
    private lateinit var binding : ItemCategoryListPopupWindowBinding
    init {
        mActivity = activity
//        mDataSource = dataSource
        layoutInflater = mActivity!!.layoutInflater
//        this.clickDeleteButtonListener = clickDeleteButtonListener
    }
    override fun getCount(): Int {
        return categories.value?.size!!
    }

    override fun getItem(p0: Int): Category {
        return categories.value?.get(p0)!!
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val listItemView: View

        if (convertView == null) {
            // If convertView is null, it means a new view needs to be created.
            val inflater = LayoutInflater.from(parent?.context)
            binding = ItemCategoryListPopupWindowBinding.inflate(inflater, parent, false)
            listItemView = binding.root

            holder = ViewHolder()
            holder.title = binding.categoryTitle

            // Set the ViewHolder as a tag on the view to reuse later.
            listItemView.tag = holder
        } else {
            // If convertView is not null, it means a recycled view can be reused.
            // Retrieve the ViewHolder from the recycled view's tag.

            listItemView = convertView
            holder = convertView.tag as ViewHolder
        }

        // Now you can bind your data to the view holder.
        holder.title?.text = getItem(position).title
        return listItemView
    }

    class ViewHolder {
        var title: TextView? = null
    }

    // interface to return callback to activity
//    interface OnClickDeleteButtonListener {
//        fun onClickDeleteButton(position: Int)
//    }
}