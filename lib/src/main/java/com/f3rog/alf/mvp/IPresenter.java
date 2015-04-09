package com.f3rog.alf.mvp;

/**
 * Interface for MVP Presenter
 *
 * @author f3rog
 */
public interface IPresenter {

    /**
     * This function should be used to unregister from event bus or any other necessary action
     */
    public void close();

}
