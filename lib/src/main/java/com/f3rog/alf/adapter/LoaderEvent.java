package com.f3rog.alf.adapter;

import java.util.List;

/**
 * {@link com.f3rog.alf.adapter.LoaderEvent}
 *
 * @param <T> Class of items for adapter.
 * @param <S> Class of sort data.
 * @author f3rog
 * @version 2015-01-27
 */
public class LoaderEvent<T, S> {

    private long _target;
    private S _sortData;
    private List<T> _items;
    private List<Integer> _vts;
    private long _position;
    private LoadType _loadType;

    public LoaderEvent() {
    }

    public long getTarget() {
        return _target;
    }

    public S getSortData() {
        return _sortData;
    }

    public List<T> getItems() {
        return _items;
    }

    public List<Integer> getVTs() {
        return _vts;
    }

    public LoadType getType() {
        return _loadType;
    }

    public long getPosition() {
        return _position;
    }

    public void setTarget(long target) {
        this._target = target;
    }

    public void setSortData(S sortData) {
        this._sortData = sortData;
    }

    public void setItems(List<T> items) {
        this._items = items;
    }

    public void setVTs(List<Integer> vts) {
        this._vts = vts;
    }

    public void setType(LoadType type) {
        this._loadType = type;
    }

    public void setPosition(long position) {
        this._position = position;
    }
}
