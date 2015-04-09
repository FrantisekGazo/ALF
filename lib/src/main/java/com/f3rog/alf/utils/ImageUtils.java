package com.f3rog.alf.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.ByteArrayOutputStream;

/**
 * Class {@link ImageUtils} contains various functions for working with images.
 *
 * @author f3rog
 * @version 2015-03-08
 */
public class ImageUtils {

	public static byte[] bitmapToByte(Bitmap img) {
		if (img == null) {
			return new byte[0];
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		img.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
		return baos.toByteArray();
	}

	public static Bitmap byteToBitmap(byte[] array) {
		if (array == null || array.length <= 1) {
			return null;
		} else {
			return BitmapFactory.decodeByteArray(array, 0, array.length);
		}
	}

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}
