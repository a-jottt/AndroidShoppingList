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
 * Created by joanna on 29.06.16.
 */
public class ListTitleDialogFragment extends DialogFragment implements TextView.OnEditorActionListener {

    public interface ListTitleDialogListener {
        void onFinishEditDialog(String inputText);
    }

    private EditText mEditText;
    private TextInputLayout activationCodeWrapper;

    public ListTitleDialogFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_list, container, false);
        mEditText = (EditText) view.findViewById(R.id.txt_licence);
        activationCodeWrapper = (TextInputLayout) view.findViewById(R.id.activationCodeWrapper);
        ImageButton imageButtonClose = (ImageButton) view.findViewById(R.id.imageButtonClose);
        imageButtonClose.setOnClickListener(closeDialogListener);

        Button activateButton = (Button) view.findViewById(R.id.activateButton);
        activateButton.setOnClickListener(v -> attemptListCreated());

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mEditText.setOnEditorActionListener(this);

        return view;
    }

    final View.OnClickListener closeDialogListener = view -> getDialog().cancel();

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {

            if (attemptListCreated()) {
                return true;
            }
        }
        return false;
    }

    private boolean attemptListCreated() {
        String title = mEditText.getText().toString().trim();
        View focusView = null;
        boolean cancel = false;

        if (title.length() == 0) {
            activationCodeWrapper.setError("List title should not be blank");
            focusView = mEditText;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
            return false;
        } else {
            returnInputTextToActivity(title);
            return true;
        }
    }

    private void returnInputTextToActivity(String title) {
        ListTitleDialogListener activity = (ListTitleDialogListener) getActivity();
        activity.onFinishEditDialog(title);
        getDialog().dismiss();

    }
}
