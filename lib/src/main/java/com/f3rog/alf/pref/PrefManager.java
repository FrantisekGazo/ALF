package com.f3rog.alf.pref;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f3rog.alf.dagger.qualifier.ForApplication;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Class {@link PrefManager} is used for managing shared preferences.<br>
 * Commit is not necessary in this class.
 *
 * @author f3rog
 * @version 2015-02-28
 */
public class PrefManager implements SharedPreferences, SharedPreferences.Editor {

    @Inject
    static PrefManager s_prefManager;

    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.S";

    private Context _context;
    private SharedPreferences _sp;

    @Inject
    public PrefManager(@ForApplication Context c) {
        this._context = c;
        this._sp = PreferenceManager.getDefaultSharedPreferences(c);
    }

    public static Gson gson() {
        return new GsonBuilder().setDateFormat(DATETIME_FORMAT).create();
    }

    private String getKey(IKey pk) {
        return _context.getString(pk.getKey());
    }

    private <T> PrefManager putObject(IKey<T> pk, T value) {
        if (pk == null)
            throw new IllegalStateException();

        if (value instanceof String) {
            putString(getKey(pk), (String) value);
        } else if (value instanceof Integer) {
            putInt(getKey(pk), (Integer) value);
        } else if (value instanceof Long) {
            putLong(getKey(pk), (Long) value);
        } else if (value instanceof Float) {
            putFloat(getKey(pk), (Float) value);
        } else if (value instanceof Boolean) {
            putBoolean(getKey(pk), (Boolean) value);
        } else {
            // create json only if value is unknown object
            try {
                String json = gson().toJson(value);
                putString(getKey(pk), json);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        pk.doAfterPut();

        return this;
    }

    public static <T> PrefManager put(IKey<T> pk, T value) {
        return s_prefManager.putObject(pk, value);
    }

    private <T> T getObject(IKey<T> pk, T defaultValue) {
        if (pk == null)
            throw new IllegalStateException();

        Object ret = defaultValue;

        if (defaultValue instanceof String) {
            ret = getString(getKey(pk), (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            ret = getInt(getKey(pk), (Integer) defaultValue);
        } else if (defaultValue instanceof Long) {
            ret = getLong(getKey(pk), (Long) defaultValue);
        } else if (defaultValue instanceof Float) {
            ret = getFloat(getKey(pk), (Float) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            ret = getBoolean(getKey(pk), (Boolean) defaultValue);
        } else {
            String json = getString(getKey(pk), null);
            // parse json only if value is unknown object
            if (json != null) {
                try {
                    ret = gson().fromJson(json, pk.getDefaultValue().getClass());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        return (T) ret;
    }

    public static <T> T get(IKey<T> pk, T defaultValue) {
        return s_prefManager.getObject(pk, defaultValue);
    }

    public static <T> T get(IKey<T> pk) {
        return s_prefManager.getObject(pk, pk.getDefaultValue());
    }

    private PrefManager removeObjects(IKey... pks) {
        Editor editor = _sp.edit();
        for (IKey pk : pks) {
            remove(getKey(pk));
        }
        editor.commit();
        return this;
    }

    public static PrefManager remove(IKey... pks) {
        return s_prefManager.removeObjects(pks);
    }

    // --- OVERRIDDEN METHODS ------------------------------------------------------------------

    @Override
    public Map<String, ?> getAll() {
        return _sp.getAll();
    }

    @Override
    public String getString(String key, String defValue) {
        return _sp.getString(key, defValue);
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return _sp.getStringSet(key, defValues);
    }

    @Override
    public int getInt(String key, int defValue) {
        return _sp.getInt(key, defValue);
    }

    @Override
    public long getLong(String key, long defValue) {
        return _sp.getLong(key, defValue);
    }

    @Override
    public float getFloat(String key, float defValue) {
        return _sp.getFloat(key, defValue);
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        return _sp.getBoolean(key, defValue);
    }

    @Override
    public boolean contains(String key) {
        return _sp.contains(key);
    }

    @Override
    public Editor edit() {
        return _sp.edit();
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        _sp.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        _sp.unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public Editor putString(String key, String value) {
        _sp.edit().putString(key, value).commit();
        return _sp.edit();
    }

    @Override
    public Editor putStringSet(String key, Set<String> values) {
        _sp.edit().putStringSet(key, values).commit();
        return _sp.edit();
    }

    @Override
    public Editor putInt(String key, int value) {
        _sp.edit().putInt(key, value).commit();
        return _sp.edit();
    }

    @Override
    public Editor putLong(String key, long value) {
        _sp.edit().putLong(key, value).commit();
        return _sp.edit();
    }

    @Override
    public Editor putFloat(String key, float value) {
        _sp.edit().putFloat(key, value).commit();
        return _sp.edit();
    }

    @Override
    public Editor putBoolean(String key, boolean value) {
        _sp.edit().putBoolean(key, value).commit();
        return _sp.edit();
    }

    @Override
    public Editor remove(String key) {
        _sp.edit().remove(key).commit();
        return _sp.edit();
    }

    @Override
    public Editor clear() {
        _sp.edit().clear().commit();
        return _sp.edit();
    }

    @Override
    public boolean commit() {
        return _sp.edit().commit();
    }

    @Override
    public void apply() {
        _sp.edit().apply();
    }
}
