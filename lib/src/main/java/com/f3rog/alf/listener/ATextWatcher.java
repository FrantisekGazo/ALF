package com.f3rog.alf.listener;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Class {@link ATextWatcher}
 *
 * @author f3rog
 */
public abstract class ATextWatcher implements TextWatcher {

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}