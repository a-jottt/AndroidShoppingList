package com.example.androidshoppinglist.views;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.androidshoppinglist.R;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public interface ProductDialogListener {
        void onFinishEditDialog(String inputText, double quantity);
    }

    private EditText mEditTextName;
    private EditText mEditTextQuantity;
    private TextInputLayout mProductWrapper;
    private TextInputLayout mQuantityWrapper;

    public ProductDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_product, container, false);
        mEditTextName = (EditText) view.findViewById(R.id.editTextProductName);
        mEditTextQuantity = (EditText) view.findViewById(R.id.editTextQuantity);
        mProductWrapper = (TextInputLayout) view.findViewById(R.id.productWrapper);
        mQuantityWrapper = (TextInputLayout) view.findViewById(R.id.quantityWrapper);
        ImageButton imageButtonClose = (ImageButton) view.findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(closeDialogListener);

        Button activateButton = (Button) view.findViewById(R.id.activateButton);
        activateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                attemptProductCreated();
            }
        });

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mEditTextName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditTextName.setOnEditorActionListener(this);
        mEditTextQuantity.setOnEditorActionListener(this);

        return view;
    }

    final View.OnClickListener closeDialogListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getDialog().cancel();
        }
    };

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {

            if (attemptProductCreated()) {
                return true;
            }
        }
        return false;
    }

    private boolean attemptProductCreated() {
        String name = mEditTextName.getText().toString().trim();
        double quantity = Double.valueOf(mEditTextQuantity.getText().toString());
        View focusView = null;
        boolean cancel = false;

        if (name.length() == 0) {
            mProductWrapper.setError("Product name should not be blank");
            focusView = mEditTextName;
            cancel = true;
        }
        if (quantity == 0) {
            mQuantityWrapper.setError("Quantity should be set");
            focusView = mEditTextQuantity;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            returnInputTextToActivity(name, quantity);
            return true;
        }
    }

    private void returnInputTextToActivity(String title, double quantity) {
        ProductDialogListener activity = (ProductDialogListener) getActivity();
        activity.onFinishEditDialog(title, quantity);
        getDialog().dismiss();

    }
}
