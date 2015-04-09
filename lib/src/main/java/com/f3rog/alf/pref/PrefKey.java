package com.f3rog.alf.pref;

import com.f3rog.alf.R;

/**
 * Class {@link PrefKey} is used for storing all shared preference keys.
 *
 * @author f3rog
 * @version 2015-02-28
 */
public class PrefKey {

    public static KEY<String> EXAMPLE = new KEY<>(R.string.app_name, "DefaultValue");

    public static class KEY<T> implements IKey<T> {

        private int _resName;
        private T _defaultValue;

        public KEY(int res, T defaultValue) {
            if (defaultValue == null)
                throw new IllegalArgumentException();
            _resName = res;
            _defaultValue = defaultValue;
        }

        @Override
        public int getKey() {
            return _resName;
        }

        @Override
        public void doAfterPut() {
        }

        @Override
        public T getDefaultValue() {
            return _defaultValue;
        }
    }

}
