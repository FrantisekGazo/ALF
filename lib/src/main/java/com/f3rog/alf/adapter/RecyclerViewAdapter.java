package com.f3rog.alf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f3rog.alf.R;
import com.f3rog.alf.database.IObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class {@link com.f3rog.alf.adapter.RecyclerViewAdapter}
 *
 * @author f3rog
 * @version 2015-01-12
 */
public abstract class RecyclerViewAdapter<T extends IObject> extends RecyclerView.Adapter<AViewHolder> {

    /**
     * Interface {@link com.f3rog.alf.adapter.RecyclerViewAdapter.OnItemClickListener} for listening on item click events.
     *
     * @param <T> Item type
     */
    public static interface OnItemClickListener<T> {
        public void onItemClick(T item, View v);
    }

    /**
     * Interface {@link com.f3rog.alf.adapter.RecyclerViewAdapter.OnItemClickListener} for listening on item long click events.
     *
     * @param <T> Item type
     */
    public static interface OnItemLongClickListener<T> {
        public boolean onItemLongClick(T item, View v);
    }

    /**
     * Class {@link com.f3rog.alf.adapter.RecyclerViewAdapter.FooterViewHolder}
     */
    public static class FooterViewHolder extends AViewHolder {

        public static final int RES = R.layout.list_empty;

        public FooterViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, RES);
        }
    }

    /**
     * Class {@link com.f3rog.alf.adapter.RecyclerViewAdapter.ProgressViewHolder}
     */
    public static class ProgressViewHolder extends AViewHolder {

        public static final int RES = R.layout.list_progress;

        public ProgressViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater, parent, RES);
        }
    }

    protected static final int VT_PROGRESS = 1;
    protected static final int VT_EMPTY_MSG = 2;
    public static final int VT_HEADER = 3;
    public static final int VT_FOOTER = 4;
    public static final int VT_ITEM = 5;
    public static final int VT_GROUP = 6;

    private Context _context;
    protected final LayoutInflater _inflater;

    private List<T> _items = new ArrayList<>();
    private List<Integer> _vts = new ArrayList<>();
    private HashMap<Integer, OnItemClickListener<T>> _clickListeners = new HashMap<>();
    private HashMap<Integer, OnItemLongClickListener<T>> _longClickListeners = new HashMap<>();

    /**
     * Constructor.<br>
     * Call {@link com.f3rog.alf.adapter.RecyclerViewAdapter#setData(java.util.List<T>)}
     *
     * @param c Context
     */
    public RecyclerViewAdapter(Context c) {
        _context = c;
        _inflater = LayoutInflater.from(c);
    }

    /**
     * Resets shown data.
     *
     * @param items Items
     */
    public void setData(List<T> items) {
        _vts = new ArrayList<>();
        if (items != null) {
            _items = items;
            for (int i = 0; i < _items.size(); i++) {
                _vts.add(VT_ITEM);
            }
        } else {
            _items = new ArrayList<>();
        }
        notifyDataSetChanged();
        checkEmpty();
    }

    /**
     * Adds given item with given view type at given position.
     *
     * @param position Item position
     * @param item     Item
     * @param vt       View type
     */
    public void add(int position, T item, Integer vt) {
        if (position < 0) {
            return;
        }
        _items.add(position, item);
        _vts.add(position, vt);
        notifyItemInserted(position);
        checkEmpty();
    }

    /**
     * Adds given item with given view type.
     *
     * @param item Item
     * @param vt   View type
     */
    public void add(T item, Integer vt) {
        _items.add(item);
        _vts.add(vt);
        notifyItemInserted(_items.size() - 1);
        checkEmpty();
    }

    /**
     * Adds given item at given position.
     *
     * @param position Item position
     * @param item     Item
     */
    public void add(int position, T item) {
        add(position, item, VT_ITEM);
    }

    /**
     * Adds given item.
     *
     * @param item Item
     */
    public void add(T item) {
        add(item, VT_ITEM);
    }

    /**
     * Adds all given items.
     *
     * @param items Items to add.
     * @return Count of added items.
     */
    public int addAll(List<T> items) {
        return addAll(items, null);
    }

    public int addAll(List<T> items, List<Integer> vts) {
        if (items == null)
            return 0;
        int startPos = getItemCount() - 1;
        _items.addAll(items);
        if (vts != null && vts.size() == items.size()) {
            _vts.addAll(vts);
        } else {
            for (int i = 0; i < items.size(); i++) {
                _vts.add(VT_ITEM);
            }
        }
        // if adapter was empty before => just notify
        if (startPos == 0) {
            notifyDataSetChanged();
        } else { // else notify inserted
            notifyItemRangeInserted(startPos, items.size());
        }
        checkEmpty();
        return items.size();
    }

    /**
     * Removes item at given position.
     *
     * @param position Item position
     */
    public void remove(int position) {
        if (position < 0 || position >= _vts.size()) {
            return;
        }
        _items.remove(position);
        _vts.remove(position);
        notifyItemRemoved(position);
        checkEmpty();
    }

    /**
     * Removes given item.
     *
     * @param item Item
     */
    public void remove(T item) {
        int position = getItemPosition(item);
        if (position >= 0) {
            _items.remove(position);
            _vts.remove(position);
            notifyItemRemoved(position);
            checkEmpty();
        }
    }

    public void removeAll() {
        _items.clear();
        _vts.clear();
        notifyDataSetChanged();
    }

    /**
     * Changes position and view type of given item.
     *
     * @param newPosition New position for given item
     * @param item        Item
     * @param vt          View type
     */
    public void change(int newPosition, T item, Integer vt) {
        if (newPosition < 0 || newPosition >= _vts.size()) {
            return;
        }
        int position = getItemPosition(item);
        if (position >= 0) {
            _items.remove(position);
            _items.add(newPosition, item);
            _vts.remove(position);
            _vts.add(newPosition, vt);
            notifyItemMoved(position, newPosition);
            notifyItemChanged(newPosition);
            checkEmpty();
        }
    }

    /**
     * Changes position of given item.<br>
     * View type will not be changed.
     *
     * @param newPosition New position for given item
     * @param item        Item
     */
    public void change(int newPosition, T item) {
        if (newPosition < 0 || newPosition >= _vts.size()) {
            return;
        }
        int position = getItemPosition(item);
        if (position >= 0) {
            _items.remove(position);
            _items.add(newPosition, item);
            Integer vt = _vts.remove(position);
            _vts.add(newPosition, vt);
            notifyItemMoved(position, newPosition);
            notifyItemChanged(newPosition);
        }
    }

    public void changeAll(List<T> items) {
        changeAll(items, null);
    }

    public void changeAll(List<T> items, List<Integer> vts) {
        if (items == null) {
            return;
        }
        // remove previous items
        _items.clear();
        _vts.clear();
        // add new items
        _items.addAll(items);
        if (vts != null && vts.size() == items.size()) {
            _vts.addAll(vts);
        } else {
            for (int i = 0; i < items.size(); i++) {
                _vts.add(VT_ITEM);
            }
        }
        notifyDataSetChanged();
        checkEmpty();
    }

    /**
     * Changes view type of item at given position.
     *
     * @param position Item position
     * @param vt       View type
     */
    public void changeItemViewType(int position, Integer vt) {
        if (position < 0 || position >= _vts.size()) {
            return;
        }
        _vts.set(position, vt);
        notifyItemChanged(position);
        checkEmpty();
    }

    @Override
    public int getItemViewType(int position) {
        return _vts.get(position);
    }

    /**
     * Checks if adapter contains some item with given view type.
     *
     * @param vt View type
     * @return True if contains
     */
    protected boolean hasViewType(int vt) {
        return _vts.contains(vt);
    }

    /**
     * Checks if adapter is empty.<br>
     * By default checks only items of view type {@link com.f3rog.alf.adapter.RecyclerViewAdapter#VT_ITEM}.
     *
     * @return True if it is empty
     */
    public boolean isEmpty() {
        return !hasViewType(VT_ITEM);
    }

    /**
     * Returns given item's position. Uses function {@link com.f3rog.alf.adapter.RecyclerViewAdapter#equals(T, T)}.
     *
     * @param item Item
     * @return Position of given item. If not found or item is null returns -1.
     */
    public int getItemPosition(T item) {
        if (item == null) {
            return -1;
        }
        for (int i = 0; i < _items.size(); i++) {
            if (equals(item, _items.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Returns item from given position.
     *
     * @param position Item position.
     * @return If position is invalid returns null.
     */
    protected T getItemAtPosition(int position) {
        if (position < 0 || position >= _items.size()) {
            return null;
        }
        return _items.get(position);
    }

    /**
     * Returns context.
     */
    protected Context getContext() {
        return _context;
    }

    /**
     * Compares two given items.
     *
     * @param item1 First item
     * @param item2 Second item
     * @return true if given items are equal.
     */
    protected boolean equals(T item1, T item2) {
        return item1 != null && item2 != null && item1.getId().equals(item2.getId());
    }

    @Override
    public int getItemCount() {
        return _items.size();
    }

    /**
     * Returns the total number of items of given view type in the data set hold by the adapter.
     *
     * @param viewType View type
     * @return Total number of items of given view type
     */
    public int getCount(int viewType) {
        int count = 0;
        for (int i = 0; i < _vts.size(); i++) {
            count += (_vts.get(i) == viewType) ? 1 : 0;
        }
        return count;
    }

    /**
     * Sets on item click listener.
     *
     * @param viewType View type
     * @param listener Listener
     */
    public void setOnItemClickListener(int viewType, OnItemClickListener<T> listener) {
        if (listener != null)
            _clickListeners.put(viewType, listener);
    }

    /**
     * Sets on item long click listener.
     *
     * @param viewType View type
     * @param listener Listener
     */
    public void setOnItemLongClickListener(int viewType, OnItemLongClickListener<T> listener) {
        if (listener != null)
            _longClickListeners.put(viewType, listener);
    }

    @Override
    public AViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AViewHolder viewHolder = onCreateViewHolderBeforeSuper(parent, viewType);
        if (viewHolder == null) {
            switch (viewType) {
                case VT_PROGRESS:
                    viewHolder = new ProgressViewHolder(_inflater, parent);
                    break;
                case VT_EMPTY_MSG:
                    viewHolder = new FooterViewHolder(_inflater, parent);
                    break;
            }
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AViewHolder holder, int position) {
        final int vt = getItemViewType(position);
        final T item = getItemAtPosition(position);
        if (_clickListeners.containsKey(vt)) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _clickListeners.get(vt).onItemClick(item, v);
                }
            });
        }
        if (_longClickListeners.containsKey(vt)) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return _longClickListeners.get(vt).onItemLongClick(item, v);
                }
            });
        }
        onBindViewHolderAfterListenerSet(holder, position);
    }

    /**
     * Checks if adapter is empty. If it is empty, shows footer.
     */
    private void checkEmpty() {
        if (isEmpty()) {
            if (!hasViewType(VT_EMPTY_MSG)) {
                add(null, VT_EMPTY_MSG);
            }
        } else {
            removeOneFor(VT_EMPTY_MSG);
        }
    }

    protected void removeAllFor(int vt) {
        int delete;
        while ((delete = _vts.indexOf(vt)) > -1) {
            remove(delete);
        }
    }

    protected void removeOneFor(int vt) {
        int delete;
        if ((delete = _vts.indexOf(vt)) > -1) {
            remove(delete);
        }
    }

    /**
     * This function is called after all listeners were set.
     *
     * @param holder   View holder
     * @param position Position
     */
    protected abstract void onBindViewHolderAfterListenerSet(RecyclerView.ViewHolder holder, int position);

    /**
     * Creates view holder for given view type.
     *
     * @param parent   Parent container
     * @param viewType View type
     * @return View holder. NULL if given view type is not supported.
     */
    protected abstract AViewHolder onCreateViewHolderBeforeSuper(ViewGroup parent, int viewType);

}
