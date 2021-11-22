package com.dkb.presentation.ui.base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.koin.core.component.KoinComponent

open class BaseRecyclerViewAdapterWrapper<T>(
    list: MutableList<T> = mutableListOf(),
    private val getHolder: (parent: ViewGroup, viewType: Int) -> BaseViewHolder<T>
) : BaseRecyclerViewAdapter<T>(list), KoinComponent {
    override fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return getHolder(parent, viewType)
    }
}

abstract class BaseRecyclerViewAdapter<T>(
    list: MutableList<T> = mutableListOf()
) : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected open val itemsComparator: (T, T) -> Boolean = { first, second -> first?.equals(second) == true }

    private var _list: MutableList<T> = list
        set(value) {
            notifyWithDiffUtils(field, value, itemsComparator)
            field = value
        }

    fun getList(): List<T> = _list

    fun clear() {
        _list.clear()
        notifyDataSetChanged()
    }

    fun setList(newList: List<T>) {
        _list = newList.toMutableList()
    }

    fun addList(newList: List<T>) {
        val oldSize = _list.size
        _list.addAll(newList)
        notifyItemRangeInserted(oldSize, _list.size - oldSize)
    }

    fun addList(startIndex: Int, newList: List<T>) {
        _list.addAll(startIndex, newList)
        notifyItemRangeInserted(startIndex, newList.size)
    }

    fun addItem(item: T, position: Int = _list.size) {
        _list.add(position, item)
        notifyItemInserted(position)
    }

    fun deleteItem(item: T) {
        val position = _list.indexOf(item)
        deleteItem(position)
    }

    fun deleteItem(position: Int): T {
        notifyItemRemoved(position)
        return _list.removeAt(position)
    }

    fun getItemAt(position: Int): T = _list[position]

    override fun getItemCount(): Int {
        return _list.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        holder.bindWithPos(_list[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        return createHolder(parent, viewType)
    }

    abstract fun createHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

}

open class BaseViewHolder<T>(
    view: View,
    val bind: (T) -> Unit,
    val bindWithPos: (T, Int) -> Unit = { item, _ -> bind(item) }
) : RecyclerView.ViewHolder(view)

abstract class BaseViewHolderProvider<T> : KoinComponent {
    abstract fun createViewHolder(parent: ViewGroup, inflater: LayoutInflater): BaseViewHolder<T>
}

fun <T> RecyclerView.Adapter<*>.notifyWithDiffUtils(
    oldList: List<T>,
    newList: List<T>,
    itemsComparator: (T, T) -> Boolean
) {
    val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return itemsComparator(oldList[oldItemPosition], newList[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size
    })

    diff.dispatchUpdatesTo(this)
}