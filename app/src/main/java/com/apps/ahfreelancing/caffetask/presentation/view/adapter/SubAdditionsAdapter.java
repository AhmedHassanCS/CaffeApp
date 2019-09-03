package com.apps.ahfreelancing.caffetask.presentation.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.ahfreelancing.caffetask.R;
import com.apps.ahfreelancing.caffetask.presentation.model.Addition;
import com.apps.ahfreelancing.caffetask.presentation.model.SubAddition;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ahmed Hassan on 9/3/2019.
 */
public class SubAdditionsAdapter extends RecyclerView.Adapter<SubAdditionsAdapter.SubAdditionHolder>{

    public ArrayList<SubAddition> subAdditions;
    private AdditionsAdapter.AdditionCallback additionCallback;
    private Context context;

    public SubAdditionsAdapter(ArrayList<SubAddition> subAdditions, AdditionsAdapter.AdditionCallback additionCallback, Context context) {
        this.subAdditions = subAdditions;
        this.additionCallback = additionCallback;
        this.context = context;
    }

    @NonNull
    @Override
    public SubAdditionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_addition, parent, false);
        return new SubAdditionHolder(inflatedView, additionCallback, context);
    }

    @Override
    public void onBindViewHolder(@NonNull SubAdditionHolder holder, int position) {
        super.onViewAttachedToWindow(holder);
        holder.bind(subAdditions.get(position), position);
    }

    @Override
    public int getItemCount() {
        return subAdditions.size();
    }

    class SubAdditionHolder extends RecyclerView.ViewHolder {

        private AdditionsAdapter.AdditionCallback additionCallback;
        private Context context;

        @BindView(R.id.subAdditionTitleTextView)
        TextView titleTextView;

        @BindView(R.id.subAdditionImageView)
        ImageView subAdditionImageView;

        @BindView(R.id.subAdditionLayout)
        LinearLayout mainLayout;

        public SubAdditionHolder(@NonNull View itemView, AdditionsAdapter.AdditionCallback additionCallback, Context context) {
            super(itemView);
            this.additionCallback = additionCallback;
            this.context = context;
            ButterKnife.bind(this, itemView);
        }

        public void bind(SubAddition subAddition, final int index){
            titleTextView.setText(subAddition.getTitle());
            if(subAddition.isChosen())
                mainLayout.setBackground(ContextCompat.getDrawable(context, R.drawable.white_rounded_border_bg));
            else mainLayout.setBackgroundResource(R.color.colorWhite);

            Uri imageUri = Uri.parse("https://smamenu.com/Upload/Additions/" + subAddition.getImageURL());

            GlideToVectorYou.init().with((Activity) context).load(imageUri, subAdditionImageView);

            mainLayout.setOnClickListener(view -> {
                additionCallback.onSubAdditionChosen(subAddition.getId(), subAddition.getParent());
                subAdditions.get(index).setChosen(!subAdditions.get(index).isChosen());

                if(subAdditions.get(index).isChosen()){
                    for (int i = 0; i < subAdditions.size(); i++) {
                        if(i != index)
                            subAdditions.get(i).setChosen(false);
                    }
                }
                SubAdditionsAdapter.this.notifyDataSetChanged();
            });
        }
    }

}
