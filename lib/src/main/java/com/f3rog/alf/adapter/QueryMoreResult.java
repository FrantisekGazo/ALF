package com.f3rog.alf.adapter;

import java.util.List;

/**
 * Class {@link com.f3rog.alf.adapter.QueryMoreResult} for query page result
 *
 * @param <T> Class of returned objects
 * @author f3rog
 * @version 2015-01-19
 */
public class QueryMoreResult<T> {

    public List<T> items;
    public List<Integer> vts;
    public boolean isAll;
}
