package com.bresiu.krypto.utils.util;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;

import com.bresiu.krypto.R;

public class ColorUtils {

    /**
     * Get gradient from table
     */
    public static GradientDrawable makeGradient(int[] tabColor) {
        return new GradientDrawable(
                GradientDrawable.Orientation.LEFT_RIGHT, tabColor);
    }

    /**
     * Get a random color from resources
     */
    public static int getNextRandomColor(Context context) {
        int[] colors = new int[]{
                R.color.turquoise,
                R.color.green_sea,
                R.color.emerald,
                R.color.nepritis,
                R.color.peter_river,
                R.color.belize_hole,
                R.color.amethyst,
                R.color.wisteria,
                R.color.wer_asphalt,
                R.color.midnight_blue,
                R.color.sun_flower,
                R.color.orange,
                R.color.carrot,
                R.color.pumpkin,
                R.color.alizarin,
                R.color.pomegrante,
                R.color.clouds,
                R.color.silver,
                R.color.concrete,
                R.color.asbestos
        };
        return colors[RandomUtils.getNextRandomInt(0, colors.length)];
    }
}
