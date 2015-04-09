package com.f3rog.alf.pref;

/**
 * Interface for preference key
 *
 * @author f3rog
 * @version 2015-02-28
 */
public interface IKey<T> {

    /**
     * Returns resource for String key.
     *
     * @return res
     */
    public int getKey();

    /**
     * Performs some action after putting new value to this preference key
     */
    public void doAfterPut();

    /**
     * Returns default value for this key
     */
    public T getDefaultValue();

}
