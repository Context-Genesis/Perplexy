package com.contextgenesis.perplexy.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Window;
import android.widget.Toast;

import com.contextgenesis.perplexy.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankDialog extends Dialog {

    @Bind(R.id.cardview_stack_of_coins)
    CardView stack;

    @OnClick(R.id.cardview_stack_of_coins)
    public void onClick_stack() {
        Toast.makeText(getContext(), "Purchasing stack of coins..", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.cardview_pile_of_coins)
    public void onClick_pile() {

    }

    @OnClick(R.id.cardview_bag_of_coins)
    public void onClick_bag() {

    }

    @OnClick(R.id.cardview_chest_of_coins)
    public void onClick_chest() {

    }

    @OnClick(R.id.cardview_vault_of_coins)
    public void onClick_vault() {

    }

    public BankDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_inapp_purchases);
        ButterKnife.bind(this);
    }

}