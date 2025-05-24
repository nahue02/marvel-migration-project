package org.marvel.project.ui

import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.marvel.project.data.Character
import org.marvel.project.databinding.ListItemCharacterBinding

class CharacterViewHolder(private val binding: ListItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character) {
        binding.name.text = character.name
        binding.description.text = character.description

        if (character.thumbnailUrl.isNotEmpty()) {
            Picasso.get()
                .load(character.thumbnailUrl)
                .into(binding.image)
        } else {
            binding.image.setImageURI(null)
        }
    }

}