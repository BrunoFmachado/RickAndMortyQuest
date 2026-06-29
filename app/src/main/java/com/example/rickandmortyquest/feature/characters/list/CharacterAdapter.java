package com.example.rickandmortyquest.feature.characters.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rickandmortyquest.databinding.ItemCharacterBinding;
import com.example.rickandmortyquest.domain.model.CharacterItem;

import java.util.Objects;

public class CharacterAdapter extends ListAdapter<CharacterItem, CharacterAdapter.CharacterViewHolder> {

    public interface OnCharacterClickListener {
        void onCharacterClick(CharacterItem character);
    }

    private final OnCharacterClickListener listener;

    public CharacterAdapter(OnCharacterClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<CharacterItem> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CharacterItem>() {
                @Override
                public boolean areItemsTheSame(@NonNull CharacterItem oldItem, @NonNull CharacterItem newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull CharacterItem oldItem, @NonNull CharacterItem newItem) {
                    return Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getStatus(), newItem.getStatus())
                            && Objects.equals(oldItem.getSpecies(), newItem.getSpecies())
                            && Objects.equals(oldItem.getGender(), newItem.getGender())
                            && Objects.equals(oldItem.getLocationName(), newItem.getLocationName())
                            && Objects.equals(oldItem.getImageUrl(), newItem.getImageUrl());
                }
            };

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        private final ItemCharacterBinding binding;

        CharacterViewHolder(ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CharacterItem character) {
            binding.nameTextView.setText(character.getName());
            binding.statusTag.setText(character.getStatus());
            binding.speciesTag.setText(character.getSpecies());
            binding.genderTextView.setText("Genero: " + character.getGender());
            binding.locationTextView.setText("Localizacao: " + character.getLocationName());

            Glide.with(binding.characterImageView.getContext())
                    .load(character.getImageUrl())
                    .centerCrop()
                    .into(binding.characterImageView);

            binding.rootCard.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onCharacterClick(character);
                }
            });
        }
    }
}
