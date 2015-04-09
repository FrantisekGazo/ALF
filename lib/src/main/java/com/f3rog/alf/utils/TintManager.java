package com.f3rog.alf.utils;

import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.Menu;

/**
 * Class {@link TintManager}
 *
 * @author f3rog
 * @version 2015-01-15
 */
public class TintManager {

    public static Drawable mutateAndTintIcon(Context c, int iconRes, int colorRes) {
        return mutateAndTintIcon(c.getResources().getDrawable(iconRes), colorRes);
    }

    public static Drawable mutateAndTintIcon(Drawable icon, int colorRes) {
        TintManager tm = new TintManager(colorRes);
        return tm.mutateAndTintIcon(icon);
    }

    private static ColorFilter getFilter(int color) {
        int red = (color & 0xFF0000) / 0xFFFF;
        int green = (color & 0xFF00) / 0xFF;
        int blue = color & 0xFF;

        float[] matrix = {0, 0, 0, 0, red
                , 0, 0, 0, 0, green
                , 0, 0, 0, 0, blue
                , 0, 0, 0, 1, 0};

        return new ColorMatrixColorFilter(matrix);
    }

    private ColorFilter _filter;

    /**
     * Constructor
     *
     * @param color Color
     */
    public TintManager(int color) {
        _filter = getFilter(color);
    }

    /**
     * Tints given icon.
     *
     * @param icon Make sure to call mutate() on this object.
     */
    public void tintIcon(Drawable icon) {
        icon.setColorFilter(_filter);
    }

    /**
     * Mutates and tints given icon.
     *
     * @param icon Icon
     */
    public Drawable mutateAndTintIcon(Drawable icon) {
        Drawable mutateIcon = icon.mutate();
        mutateIcon.setColorFilter(_filter);
        return mutateIcon;
    }

    /**
     * Tints all menu icons.
     *
     * @param menu Menu
     */
    public void tintMenu(Menu menu) {
        Drawable icon;
        for (int i = 0; i < menu.size(); i++) {
            try {
                icon = menu.getItem(i).getIcon().mutate();
                icon.setColorFilter(_filter);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
