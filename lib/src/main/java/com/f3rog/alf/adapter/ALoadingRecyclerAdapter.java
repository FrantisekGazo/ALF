package com.f3rog.alf.adapter;

import android.content.Context;

import com.f3rog.alf.bus.Bus;
import com.f3rog.alf.database.IObject;
import com.f3rog.alf.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link com.f3rog.alf.adapter.ALoadingRecyclerAdapter} serves for dynamic loading objects to {@link android.support.v7.widget.RecyclerView}.<br>
 *
 * @param <T> Class of items shown in {@link android.support.v7.widget.RecyclerView}.
 * @param <S> Class of sort data.
 * @author f3rog
 * @version 2015-01-18
 */
public abstract class ALoadingRecyclerAdapter<T extends IObject, S> extends RecyclerViewAdapter<T> {

    /**
     * Enum {@link com.f3rog.alf.adapter.ALoadingRecyclerAdapter.InnerState} for inner adapter states.
     */
    private enum InnerState {
        NONE, LOADING, RELOADING, NO_MORE
    }

    private ALoader<T, S> _loader;
    private StartType _startType;
    private InnerState _innerState;
    private List<Long> _toAdd;
    private List<Long> _toChange;
    private long _counter;

    /**
     * Constructor. This instance
     *
     * @param c      Context
     * @param loader Loader
     */
    public ALoadingRecyclerAdapter(Context c, ALoader<T, S> loader) {
        super(c);
        if (loader == null)
            throw new IllegalStateException();
        _loader = loader;
        // init values
        _counter = 0;
        _toAdd = new ArrayList<>();
        _toChange = new ArrayList<>();
        _startType = StartType.NO;
        _innerState = InnerState.NONE;
        Bus.registerSticky(this);
    }

    /**
     * Unregister this manager from EventBus.
     */
    public void close() {
        Bus.unregister(this);
    }

    /**
     * Resumes automatic loading.
     */
    public void resume() {
        start(true);
    }

    /**
     * Starts only once.
     *
     * @param startType Start type
     */
    public void start(StartType startType) {
        if (_startType == StartType.NO) {
            _startType = startType;
            start(false);
        }
    }

    private void start(boolean resume) {
        _innerState = resume ? InnerState.RELOADING : InnerState.LOADING;
        removeOneFor(VT_PROGRESS);
        requestMore();
    }

    /**
     * Do not use this function. Data should be loaded dynamically, not by this function.
     *
     * @param items Items
     */
    @Deprecated
    @Override
    public void setData(List<T> items) {
        super.setData(items);
    }

    @Override
    public boolean isEmpty() {
        return !hasViewType(VT_ITEM) && !hasViewType(VT_PROGRESS);
    }

    @Override
    public void onBindViewHolder(AViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        // if was scrolled to one of last 3 items => start loading more (if not loading already)
        if (_innerState == InnerState.NONE && (position >= getItemCount() - 3)) {
            _innerState = InnerState.LOADING;
            holder.itemView.post(new Runnable() {
                @Override
                public void run() {
                    requestMore();
                }
            });
        }
    }

    /**
     * Requests more {@link T} objects.
     */
    private void requestMore() {
        if (!_loader.checkBeforeStart()) {
            _innerState = InnerState.NONE;
            return;
        }
        removeOneFor(VT_EMPTY_MSG);
        add(null, VT_PROGRESS);
        if (_innerState == InnerState.RELOADING) {
            doJob(LoadType.ALL, getCount(VT_ITEM));
        } else if (_innerState == InnerState.LOADING) {
            doJob(LoadType.MORE, getCount(VT_ITEM));
        }
    }

    /**
     * Tries to request more {@link T} objects. If loading is being executed, than does nothing.
     */
    public void pokeRequestMore() {
        if (_innerState != InnerState.NONE && _innerState != InnerState.NO_MORE) {
            return;
        }
        _innerState = InnerState.LOADING;
        requestMore();
    }

    /**
     * Called if {@link T} objects were loaded.
     *
     * @param items Items
     */
    public void onLoaded(List<T> items, List<Integer> vts, boolean isAll) {
        if (_innerState == InnerState.RELOADING) {
            changeAll(items, vts);
        } else {
            addAll(items, vts);
        }
        // allow more loading
        removeOneFor(VT_PROGRESS);
        _innerState = isAll ? InnerState.NO_MORE : InnerState.NONE;
    }

    /**
     * Adds one object with given ID.
     *
     * @param id Object's ID.
     */
    public void addById(long id) {
        _toAdd.add(id);
        doJob(LoadType.ONE, id);
    }

    /**
     * Changes one object with given ID.
     *
     * @param id Object's ID.
     */
    public void changeById(long id) {
        _toChange.add(id);
        doJob(LoadType.ONE, id);
    }

    /**
     * Called if {@link T} object was loaded and is ready for adding/changing.
     *
     * @param item     {@link T} Object
     * @param position Position, where given object should be.
     */
    public void onLoaded(T item, int position) {
        if (_toAdd.contains(item.getId())) {
            _toAdd.remove(item.getId());
            this.add(position, item);
        } else if (_toChange.contains(item.getId())) {
            _toChange.remove(item.getId());
            this.change(position, item);
        }
    }

    /**
     * Sets sort data for adapter.
     *
     * @param sortData Sort data.
     */
    public void setSortData(S sortData) {
        if (!_loader.setSortData(sortData)) {
            return;
        }
        // stop and request more
        _counter++; // reject currently loaded data
        _innerState = InnerState.LOADING;
        removeAll();
        requestMore();
    }

    /**
     * Executes job
     *
     * @param type  Load type
     * @param param Parameter
     */
    private void doJob(LoadType type, long param) {
        _loader.setLoadType(type);
        _loader.setStartType(_startType);
        _loader.setParam(param);
        _loader.setTarget(Utils.idForObject(this) + (++_counter));
        _loader.start();
    }

    /**
     * Called when data has been loaded and needs to be passed to adapter.
     *
     * @param event Event
     */
    public void onEventMainThread(LoaderEvent<T, S> event) {
        // check target and sort data
        if (!amITarget(event.getTarget()) || !event.getSortData().equals(_loader.getSortData())) {
            return;
        }
        Bus.removeStickyEvent(event);
        if (_startType == StartType.WAIT) _startType = StartType.GO;
        switch (event.getType()) {
            case ONE:
                onLoaded(event.getItems().get(0), (int) event.getPosition());
                break;
            case ALL:
                onLoaded(event.getItems(), event.getVTs(), true);
                break;
            case MORE:
                onLoaded(event.getItems(), event.getVTs(), false);
                break;
            default: // NONE
                break;
        }
    }

    private boolean amITarget(long targetId) {
        return (targetId - _counter) == Utils.idForObject(this);
    }
}
