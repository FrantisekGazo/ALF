package com.f3rog.alf.adapter;

import com.f3rog.alf.bus.Bus;
import com.f3rog.alf.job.Jobber;
import com.path.android.jobqueue.Job;
import com.path.android.jobqueue.Params;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@link com.f3rog.alf.adapter.ALoader} is an abstract class for loading data using Jobs.
 *
 * @param <T> Class of items for adapter.
 * @param <S> Class of sort data.
 * @author f3rog
 * @version 2015-01-27
 */
public abstract class ALoader<T, S> extends Job implements Cloneable {

    private long _target;
    private S _sortData;
    private LoadType _loadType;
    private StartType _startType;
    private long _param;

    protected ALoader(Params params, S sortData) {
        super(params);
        if (sortData == null) {
            throw new IllegalArgumentException();
        }
        _sortData = sortData;
    }

    public void setParam(long param) {
        _param = param;
    }

    public void setTarget(long target) {
        _target = target;
    }

    /**
     * Sets new sort data.
     *
     * @param sortData Sort data
     * @return True if changed
     */
    public boolean setSortData(S sortData) {
        if (sortData == null || (_sortData != null && _sortData.equals(sortData))) {
            return false;
        }
        _sortData = sortData;
        return true;
    }

    public S getSortData() {
        return _sortData;
    }

    public void setLoadType(LoadType type) {
        this._loadType = type;
    }

    public void setStartType(StartType type) {
        this._startType = type;
    }

    protected abstract long getLimitForward();

    public abstract QueryOneResult<T> loadOne(long id);

    public abstract QueryMoreResult<T> loadMore(long offset, long limit);

    @Override
    public void onRun() throws Throwable {
        LoaderEvent<T, S> event = new LoaderEvent<>();
        List<T> items = new ArrayList<>();
        switch (_loadType) {
            case ONE:
                QueryOneResult<T> res = loadOne(_param);
                items.add(res.item);
                event.setPosition(res.position);
                event.setType(_loadType);
                break;
            case MORE:
                QueryMoreResult<T> resMore = loadMore(_param, getLimitForward());
                items.addAll(resMore.items);
                event.setVTs(resMore.vts);
                event.setType(resMore.isAll ? LoadType.ALL : LoadType.MORE);
                break;
            case ALL:
                QueryMoreResult<T> resAll = loadMore(0, _param);
                items.addAll(resAll.items);
                event.setVTs(resAll.vts);
                event.setType(LoadType.MORE);
                break;
            default:
                return;
        }
        event.setItems(items);
        event.setSortData(_sortData);
        event.setTarget(_target);

        if (_startType == StartType.WAIT) {
            try {
                Thread.sleep(1000);
            } catch (Exception ignored) {
            }
        }

        Bus.postSticky(event);
    }

    @Override
    protected boolean shouldReRunOnThrowable(Throwable throwable) {
        throwable.printStackTrace();
        return false;
    }

    @Override
    public void onAdded() {
    }

    @Override
    protected void onCancel() {
    }

    /**
     * Override this function if extra check before load starts is needed;
     *
     * @return True if loading can start.
     */
    public boolean checkBeforeStart() {
        return true;
    }

    /**
     * Starts loading
     */
    public void start() {
        // every time start a new loader
        try {
            Jobber.addJob((ALoader<T, S>) this.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

}
