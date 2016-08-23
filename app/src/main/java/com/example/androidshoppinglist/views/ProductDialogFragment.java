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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidshoppinglist.R;

/**
 * Created by joanna on 03.07.16.
 */
public class ProductDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public interface ProductDialogListener {
        void onFinishEditDialog(String inputText, double quantity, String unit);
    }

    private EditText mEditTextName;
    private EditText mEditTextQuantity;
    private TextInputLayout mProductWrapper;
    private TextInputLayout mQuantityWrapper;
    private Spinner unitSpinner;

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
        unitSpinner = (Spinner) view.findViewById(R.id.unitSpinner);
        ImageButton imageButtonClose = (ImageButton) view.findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(closeDialogListener);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.unit_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unitSpinner.setAdapter(adapter);

        Button activateButton = (Button) view.findViewById(R.id.activateButton);
        activateButton.setOnClickListener(v -> attemptProductCreated());

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mEditTextName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditTextName.setOnEditorActionListener(this);
        mEditTextQuantity.setOnEditorActionListener(this);

        return view;
    }

    final View.OnClickListener closeDialogListener = view -> getDialog().cancel();

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
        String unit = unitSpinner.getSelectedItem().toString();
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
            returnInputTextToActivity(name, quantity, unit);
            return true;

        }
    }

    private void returnInputTextToActivity(String title, double quantity, String unit) {
        ProductDialogListener activity = (ProductDialogListener) getActivity();
        activity.onFinishEditDialog(title, quantity, unit);
        getDialog().dismiss();

    }
}
