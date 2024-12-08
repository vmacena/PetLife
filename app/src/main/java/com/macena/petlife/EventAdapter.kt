package com.macena.petlife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Event(val id: Int, val type: String, val date: String)

class EventAdapter(private val events: List<Event>) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var onEditClick: (Event) -> Unit = {}
    var onRemoveClick: (Event) -> Unit = {}

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val typeTextView: TextView = view.findViewById(R.id.textViewEventType)
        val dateTextView: TextView = view.findViewById(R.id.textViewEventDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.typeTextView.text = event.type
        holder.dateTextView.text = event.date

        holder.itemView.setOnClickListener {
            onEditClick(event)
        }

        holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
            menu.add("Editar").setOnMenuItemClickListener {
                onEditClick(event)
                true
            }
            menu.add("Remover").setOnMenuItemClickListener {
                onRemoveClick(event)
                true
            }
        }
    }

    override fun getItemCount(): Int = events.size
}
