package com.example.d308vacationplanner.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private String vacationName;
    private String vacationStart;
    private String vacationEnd;
    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater mInflater;
    private final Repository repository;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public ExcursionAdapter(Context context, Repository repository, String vacationName, String vacationStart, String vacationEnd) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.repository = repository;
        this.vacationStart = vacationStart;
        this.vacationEnd = vacationEnd;
        this.vacationName = vacationName;
    }

    public class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView;
        private final TextView excursionItemView2;

        public ExcursionViewHolder(@NonNull View itemView) {
            super(itemView);
            excursionItemView = itemView.findViewById(R.id.textView2);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                final Excursion excursion = mExcursions.get(position);
                Intent intent = new Intent(context, ExcursionDetails.class);
                intent.putExtra("excursionID", excursion.getExcursionID());
                intent.putExtra("excursionName", excursion.getExcursionName());
                intent.putExtra("excursionDate", excursion.getExcursionDate());
                intent.putExtra("vacationID", excursion.getVacationID());
                intent.putExtra("excursionNote", excursion.getNote());
                vacationName = ((Activity) context).getIntent().getStringExtra("vacationName");
                intent.putExtra("vacationName", vacationName);
                intent.putExtra("vacationStart", vacationStart);
                intent.putExtra("vacationEnd", vacationEnd);
                intent.putExtra("isExcursionSaved", true);
                context.startActivity(intent);
            });
        }
    }
    @NonNull
    @Override
    public ExcursionAdapter.ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionAdapter.ExcursionViewHolder holder, int position) {
        if(mExcursions != null) {
            Excursion current = mExcursions.get(position);
            holder.excursionItemView.setText(current.getExcursionName());
            int excursionID = current.getExcursionID()-1;
            holder.excursionItemView2.setText(current.getExcursionDate());
        } else {
            holder.excursionItemView.setText("No Excursion Name");
            holder.excursionItemView2.setText("No Excursion Date");

        }
    }

    @Override
    public int getItemCount() {
        if (mExcursions == null) {
            return 0;
        }
        return mExcursions.size();
    }

    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

}
