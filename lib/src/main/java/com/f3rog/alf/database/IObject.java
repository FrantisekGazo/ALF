package com.f3rog.alf.database;

import java.io.Serializable;

/**
 * Class {@link IObject} is extended by database objects.
 *
 * @author f3rog
 * @version 2015-01-19
 */
public interface IObject extends Serializable {

    public Long getId();
    public void setId(Long id);

}
