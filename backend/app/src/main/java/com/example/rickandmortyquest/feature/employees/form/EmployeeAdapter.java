package com.example.rickandmortyquest.feature.employees.form;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rickandmortyquest.databinding.ItemEmployeeBinding;
import com.example.rickandmortyquest.domain.model.Employee;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class EmployeeAdapter extends ListAdapter<Employee, EmployeeAdapter.EmployeeViewHolder> {

    public interface OnEmployeeActionListener {
        void onEdit(Employee employee);
        void onDelete(Employee employee);
    }

    private final OnEmployeeActionListener listener;

    public EmployeeAdapter(OnEmployeeActionListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }

    private static final DiffUtil.ItemCallback<Employee> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Employee>() {
                @Override
                public boolean areItemsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
                    return oldItem.getId() != null && oldItem.getId().equals(newItem.getId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Employee oldItem, @NonNull Employee newItem) {
                    return Objects.equals(oldItem.getNome(), newItem.getNome())
                            && Objects.equals(oldItem.getEmail(), newItem.getEmail())
                            && Objects.equals(oldItem.getCargo(), newItem.getCargo())
                            && Objects.equals(oldItem.getSalario(), newItem.getSalario())
                            && oldItem.isActive() == newItem.isActive();
                }
            };

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeBinding binding = ItemEmployeeBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );

        return new EmployeeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final ItemEmployeeBinding binding;

        EmployeeViewHolder(ItemEmployeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        void bind(Employee employee) {
            binding.nameTextView.setText(employee.getNome());
            binding.emailTextView.setText(employee.getEmail());
            binding.roleSalaryTextView.setText(employee.getCargo() + " - " + formatSalary(employee));
            binding.activeTextView.setText(employee.isActive() ? "Status: Ativo" : "Status: Inativo");

            binding.editButton.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onEdit(employee);
                }
            });

            binding.deleteButton.setOnClickListener(view -> {
                if (listener != null) {
                    listener.onDelete(employee);
                }
            });
        }

        private String formatSalary(Employee employee) {
            if (employee.getSalario() == null) {
                return "R$ 0,00";
            }

            return NumberFormat
                    .getCurrencyInstance(new Locale("pt", "BR"))
                    .format(employee.getSalario());
        }
    }
}