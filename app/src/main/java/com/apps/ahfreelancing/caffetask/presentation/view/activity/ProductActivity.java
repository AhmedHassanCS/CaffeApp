package com.apps.ahfreelancing.caffetask.presentation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.ahfreelancing.caffetask.R;
import com.apps.ahfreelancing.caffetask.data.entity.calls.PurchaseBody;
import com.apps.ahfreelancing.caffetask.presentation.di.ViewModelFactory;
import com.apps.ahfreelancing.caffetask.presentation.di.component.ApplicationComponent;
import com.apps.ahfreelancing.caffetask.presentation.di.component.DaggerApplicationComponent;
import com.apps.ahfreelancing.caffetask.presentation.di.module.ApiModule;
import com.apps.ahfreelancing.caffetask.presentation.di.module.ThreadModule;
import com.apps.ahfreelancing.caffetask.presentation.model.Product;
import com.apps.ahfreelancing.caffetask.presentation.view.adapter.AdditionsAdapter;
import com.apps.ahfreelancing.caffetask.presentation.view.utility.ConnectionUtility;
import com.apps.ahfreelancing.caffetask.presentation.viewmodel.ProductViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductActivity extends AppCompatActivity  implements AdditionsAdapter.AdditionCallback {

    @BindView(R.id.productTitleTextView)
    TextView titleTextView;
    @BindView(R.id.productImageView)
    ImageView productImageView;
    @BindView(R.id.ProductDescTextView)
    TextView descriptionTextView;
    @BindView(R.id.ProductQuantityTextView)
    TextView quantityTextView;

    @BindView(R.id.ProductPriceTextView)
    TextView priceTextView;
    @BindView(R.id.totalPriceTextView)
    TextView totalPriceTextView;
    @BindView(R.id.taxTextView)
    TextView taxTextView;
    @BindView(R.id.sumTextView)
    TextView priceSumTextView;

    @BindView(R.id.productMainLayout)
    ScrollView productScrollView;
    @BindView(R.id.productProgressBar)
    ProgressBar progressBar;

    @BindView(R.id.additionsRecyclerView)
    RecyclerView additionsRecyclerView;

    @BindView(R.id.submitButton)
    Button submitButton;

    private int price;
    private int tax;
    private int quantity;

    private boolean orderCompleted;

    private HashMap<Integer, Integer> additions;
    @Inject
    ViewModelFactory<ProductViewModel> viewModelFactory;
    private ProductViewModel viewModel;

    private AdditionsAdapter additionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initDagger();
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProductViewModel.class);
        additions = new HashMap<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(orderCompleted)
            return;
        subscribeViewModel();
        setupRecyclerView();
    }

    private void subscribeViewModel(){
        if(!viewModel.getProductLiveData(1).hasObservers()){
            viewModel.getProductLiveData(1).observe(this, product -> {
                bindData(product);
                progressBar.setVisibility(View.GONE);
                productScrollView.setVisibility(View.VISIBLE);
                if(additionsAdapter.additions.size() != 0)
                    return;
                additionsAdapter.additions.addAll(product.getAdditions());
                additionsAdapter.notifyDataSetChanged();
            });
        } else viewModel.updateProduct(1);
    }

    private void setupRecyclerView(){
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        additionsRecyclerView.setLayoutManager(layoutManager);

        additionsAdapter = new AdditionsAdapter(new ArrayList<>(), this, this);
        additionsRecyclerView.setAdapter(additionsAdapter);
        additionsRecyclerView.setNestedScrollingEnabled(false);
    }

    private void bindData(Product product){
        setTitle(product.getTitle());
        titleTextView.setText(product.getTitle());
        descriptionTextView.setText(product.getDescription());
        updateNumbers(Integer.parseInt(product.getPrice()), Integer.parseInt(product.getTax()), 1);

        Picasso.with(this)
                .load("https://smamenu.com/Upload/Product/" + product.getImageURL())
                .into(productImageView);
    }

    private void updateNumbers(int price, int tax, int quantity){
        this.price = price;
        this.quantity = quantity;
        this.tax = tax;

        double total = price * quantity;
        double sum = ((tax * total) / 100.0) + total;

        priceTextView.setText(price + getString(R.string.currency));
        totalPriceTextView.setText(total+ getString(R.string.currency));
        taxTextView.setText("%" + tax);
        priceSumTextView.setText(sum + getString(R.string.currency));
        quantityTextView.setText(String.valueOf(quantity));
    }

    @OnClick(R.id.quantityAddImageView)
    void onIncreaseQuantityClick(){
        quantity++;
        updateNumbers(price, tax, quantity);
    }

    @OnClick(R.id.quantityRemoveImageView)
    void onDecreaseQuantityClick(){
        if(quantity <= 1)
            return;
        quantity--;
        updateNumbers(price, tax, quantity);
    }

    private void initDagger(){
        ApplicationComponent applicationComponent =
                DaggerApplicationComponent.builder()
                        .apiModule(new ApiModule(this.getApplication()))
                        .threadModule(new ThreadModule())
                .build();
        applicationComponent.inject(this);
    }

    @Override
    public void onSubAdditionChosen(int sub, int addition) {
        if(additions.containsKey(addition)){
            if(additions.get(addition) == sub) {
                additions.remove(addition);
                return;
            }
        }
        additions.put(addition, sub);
    }

    @OnClick(R.id.submitButton)
    void onSubmitClick(){
        StringBuilder strAdditions = new StringBuilder();
        StringBuilder strSubs = new StringBuilder();
        for (int i = 0; i < additions.keySet().size(); i++){
            if(i != additions.keySet().size() - 1)
                 strAdditions.append(String.format("%s%s",additions.keySet().toArray()[i].toString(), ','));
            else strAdditions.append(additions.keySet().toArray()[i].toString());
        }

        for (int i = 0; i < additions.values().size(); i++){
            if(i != additions.values().size() - 1)
                strSubs.append(String.format("%s%s",additions.values().toArray()[i].toString(), ','));
            else strSubs.append(additions.values().toArray()[i].toString());
        }

        viewModel.submitPurchase(new PurchaseBody(strAdditions.toString(), strSubs.toString(), quantity))
                .observe(this, success -> {
                    if(success){
                        productScrollView.setVisibility(View.GONE);
                        submitButton.setCompoundDrawables(
                                null, null, ContextCompat.getDrawable(this, R.drawable.ic_check_circle) ,null);
                        submitButton.setText(R.string.submitted);

                        submitButton.setOnClickListener((a)->{
                            restartActivity();
                        });
                        orderCompleted = true;
                    } else Toast.makeText(this, R.string.try_again, Toast.LENGTH_LONG).show();
                });
    }

    private void restartActivity(){
        Intent homeIntent = new Intent(this, ProductActivity.class);
        homeIntent.setFlags(homeIntent.getFlags() |
        Intent.FLAG_ACTIVITY_NEW_TASK |
        Intent.FLAG_ACTIVITY_CLEAR_TASK |
        Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);

    }

}
