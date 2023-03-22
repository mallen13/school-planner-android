package com.mattallen.school_planning_app.Helpers;

import android.content.Context;
import android.widget.Toast;

abstract public class Helpers {

    public static void showToast(Context context, String text) {
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
