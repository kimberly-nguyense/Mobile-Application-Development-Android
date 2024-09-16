package com.example.d308vacationplanner.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private List<Vacation> mVacations;
    private final Context context;
    private final LayoutInflater mInflater;

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationItemView;

        public VacationViewHolder(@NonNull View itemView) {
            super(itemView);
            vacationItemView = itemView.findViewById(R.id.textView3);
            vacationItemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Vacation vacation = mVacations.get(position);
                Intent intent = new Intent(context, VacationDetails.class);
                intent.putExtra("vacationID", vacation.getVacationID());
                intent.putExtra("vacationName", vacation.getVacationName());
                intent.putExtra("vacationHotelName", vacation.getHotelName());
                intent.putExtra("vacationStartDate", vacation.getStartDate());
                intent.putExtra("vacationEndDate", vacation.getEndDate());
                intent.putExtra("vacationID", vacation.getVacationID());
                context.startActivity(intent);
            });
        }
    }
    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if(mVacations != null) {
            Vacation current = mVacations.get(position);
            holder.vacationItemView.setText(current.getVacationName());
        } else {
            holder.vacationItemView.setText("No Vacation Name");
        }
    }

    @Override
    public int getItemCount() {
        if (mVacations == null) {
            return 0;
        }
        return mVacations.size();
    }

    public void setVacations(List<Vacation> vacations) {
        mVacations = vacations;
        notifyDataSetChanged();
    }

}
