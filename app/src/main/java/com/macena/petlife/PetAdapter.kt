package com.macena.petlife

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class Pet(
    val id: Int,
    val name: String,
    val type: String,
    val birthDate: String,
    val color: String,
    val size: String
)

class PetAdapter(private val pets: List<Pet>) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    var onEditClick: (Pet) -> Unit = {}
    var onRemoveClick: (Pet) -> Unit = {}
    var onViewEventsClick: (Pet) -> Unit = {}

    class PetViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewName)
        val typeTextView: TextView = view.findViewById(R.id.textViewType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pet, parent, false)
        return PetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.nameTextView.text = pet.name
        holder.typeTextView.text = pet.type

        holder.itemView.setOnCreateContextMenuListener { menu, _, _ ->
            menu.setHeaderTitle("Options for ${pet.name}")
            menu.add("Edit").setOnMenuItemClickListener {
                onEditClick(pet)
                true
            }
            menu.add("Remove").setOnMenuItemClickListener {
                onRemoveClick(pet)
                true
            }
            menu.add("View Events").setOnMenuItemClickListener {
                onViewEventsClick(pet)
                true
            }
        }
    }

    override fun getItemCount(): Int = pets.size
}
