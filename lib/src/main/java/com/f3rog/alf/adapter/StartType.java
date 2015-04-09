package com.f3rog.alf.adapter;

/**
 * Class {@link com.f3rog.alf.adapter.StartType} represents start type of {@link ALoadingRecyclerAdapter}.
 *
 * @author f3rog
 * @version 2015-01-20
 */
public enum StartType {
    /**
     * Does not start. You need to call {@link com.f3rog.alf.adapter.ALoadingRecyclerAdapter#pokeRequestMore()} in order to start loading.
     */
    NO,
    /**
     * Waits before first load
     */
    WAIT,
    /**
     * Starts right away
     */
    GO;

}
