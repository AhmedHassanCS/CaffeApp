package com.apps.ahfreelancing.caffetask.presentation.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.ahfreelancing.caffetask.R;
import com.apps.ahfreelancing.caffetask.presentation.model.Addition;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class AdditionsAdapter extends RecyclerView.Adapter<AdditionsAdapter.AdditionHolder>{

    public ArrayList<Addition> additions;
    private AdditionCallback additionCallback;
    private Context context;

    public AdditionsAdapter(ArrayList<Addition> additions, AdditionCallback additionCallback, Context context) {
        this.additions = additions;
        this.additionCallback = additionCallback;
        this.context = context;
    }

    @NonNull
    @Override
    public AdditionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_addition, parent, false);
        return new AdditionHolder(inflatedView, additionCallback, context);
    }

    @Override
    public void onBindViewHolder(@NonNull AdditionHolder holder, int position) {
        super.onViewAttachedToWindow(holder);
        holder.bind(additions.get(position));
    }

    @Override
    public int getItemCount() {
        return additions.size();
    }

    class AdditionHolder extends RecyclerView.ViewHolder {

        private AdditionCallback additionCallback;
        private Context context;

        @BindView(R.id.additionTitleTextView)
        TextView titleTextView;

        @BindView(R.id.subAdditionRecyclerView)
        RecyclerView subAdditionRecyclerView;
        public AdditionHolder(@NonNull View itemView, AdditionCallback additionCallback, Context context) {
            super(itemView);
            this.additionCallback = additionCallback;
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        public void bind(Addition addition){
            titleTextView.setText(addition.getTitle());
            LinearLayoutManager layoutManager =
                    new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            subAdditionRecyclerView.setLayoutManager(layoutManager);
            SubAdditionsAdapter subAdditionsAdapter =
                    new SubAdditionsAdapter(new ArrayList<>(), additionCallback, context);

            subAdditionRecyclerView.setAdapter(subAdditionsAdapter);
            subAdditionsAdapter.subAdditions.addAll(addition.getSubAdditions());
            subAdditionsAdapter.notifyDataSetChanged();
        }
    }

    public interface AdditionCallback{
        void onSubAdditionChosen(int sub, int addition);
    }
}
