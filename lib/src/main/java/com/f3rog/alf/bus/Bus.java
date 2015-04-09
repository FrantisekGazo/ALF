package com.f3rog.alf.bus;

import javax.inject.Inject;

import de.greenrobot.event.EventBus;

/**
 * Class {@link Bus} provides EventBus for whole application
 *
 * @author f3rog
 * @version 2015-03-07
 */
public class Bus {

    @Inject
    static EventBus s_bus;

    public static void post(Object event) {
        s_bus.post(event);
    }

    public static void postSticky(Object event) {
        s_bus.postSticky(event);
    }

    public static void registerSticky(Object obj) {
        s_bus.registerSticky(obj);
    }

    public static void register(Object obj) {
        s_bus.register(obj);
    }

    public static void unregister(Object obj) {
        s_bus.unregister(obj);
    }

    public static void removeStickyEvent(Object obj) {
        s_bus.removeStickyEvent(obj);
    }

}
