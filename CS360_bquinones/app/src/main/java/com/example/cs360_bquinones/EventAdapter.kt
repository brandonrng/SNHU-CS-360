package com.example.cs360_bquinones

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.BaseAdapter

class EventAdapter(private val context: Context, private val events: List<Event>) : BaseAdapter() {

    override fun getCount(): Int = events.size

    override fun getItem(position: Int): Any = events[position]

    override fun getItemId(position: Int): Long = events[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false)
        val titleTextView = view.findViewById<TextView>(android.R.id.text1)
        val dateTimeTextView = view.findViewById<TextView>(android.R.id.text2)

        val event = events[position]
        titleTextView.text = event.title
        dateTimeTextView.text = buildString {
            append(event.date)
            append(" at ")
            append(event.time)
        }
        return view
    }
}