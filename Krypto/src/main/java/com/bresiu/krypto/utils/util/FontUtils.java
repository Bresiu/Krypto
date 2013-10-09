package com.bresiu.krypto.utils.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;

import com.bresiu.krypto.R;
import com.bresiu.krypto.utils.TypefaceSpan;

public class FontUtils {
    public static Spannable[] createSpannableList(Context context) {
        Spannable[] spannables = new Spannable[10];
        String[] numPad = new String[]{
                context.getString(R.string.one),
                context.getString(R.string.two),
                context.getString(R.string.three),
                context.getString(R.string.four),
                context.getString(R.string.five),
                context.getString(R.string.six),
                context.getString(R.string.seven),
                context.getString(R.string.eight),
                context.getString(R.string.nine),
                context.getString(R.string.zero)};

        for (int i = 0; i < numPad.length; i++) {
            spannables[i] = FontUtils.createSpannable(context, numPad[i]);
        }
        return spannables;
    }

    public static Spannable createSpannable(Context context, String input) {

        int length = input.length();
        TypefaceSpan typeFaceRobotoThin = new TypefaceSpan(context, "Roboto-Thin");

        Spannable span = new SpannableString(input);
        span.setSpan(typeFaceRobotoThin, 0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        span.setSpan(new RelativeSizeSpan(0.4f), 1, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return span;
    }
}
