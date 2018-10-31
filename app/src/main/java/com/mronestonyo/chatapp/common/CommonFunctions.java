package com.mronestonyo.chatapp.common;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;

import com.mronestonyo.chatapp.utilities.CustomTypefaceSpan;

public class CommonFunctions {

    public static SpannableString setTypeFace(Context context, String fontName, String title) {
        if (title != null) {
            Typeface font = Typeface.createFromAsset(context.getAssets(), fontName);
            SpannableString mNewTitle = new SpannableString(title);
            mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            return mNewTitle;
        } else {
            return null;
        }
    }

    public static String capitalizeWord(String word) {

        if (word != null) {

            if (word.length() > 0) {

                StringBuilder builder = new StringBuilder();

                try {

                    String[] cap_word_arr = word.toLowerCase().split(" ");

                    for (String s : cap_word_arr) {
                        String cap = s.substring(0, 1).toUpperCase() + s.substring(1);
                        builder.append(cap + " ");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return builder.toString();

            } else {

                return null;

            }

        } else {

            return null;

        }

    }
}
