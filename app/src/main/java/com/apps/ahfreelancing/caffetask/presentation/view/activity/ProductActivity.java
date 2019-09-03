package com.apps.ahfreelancing.caffetask.presentation.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.apps.ahfreelancing.caffetask.R;
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

    private int price;
    private int tax;
    private int quantity;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        subscribeViewModel();
        setupRecyclerView();
    }

    private void subscribeViewModel(){
        if(!viewModel.getProductLiveData(1).hasObservers()){
            viewModel.getProductLiveData(1).observe(this, new Observer<Product>(){
                @Override
                public void onChanged(Product product) {
                    bindData(product);
                    progressBar.setVisibility(View.GONE);
                    productScrollView.setVisibility(View.VISIBLE);
                    if(additionsAdapter.additions.size() != 0)
                        return;
                    additionsAdapter.additions.addAll(product.getAdditions());
                    additionsAdapter.notifyDataSetChanged();
                }
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

    }
}
