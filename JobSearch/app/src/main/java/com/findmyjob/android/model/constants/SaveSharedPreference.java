package com.findmyjob.android.model.constants;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

        final static String uid = "uid";
        final static String userType = "role";

        static SharedPreferences getSharedPreferences(Context ctx) {
            return PreferenceManager.getDefaultSharedPreferences(ctx);
        }

        public static void setUserId(Context ctx, String mobileNumber, String role) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.putString(uid, mobileNumber);
            editor.putString(userType, role);
            editor.apply();
        }

        public static String getUserId(Context ctx) {
            return getSharedPreferences(ctx).getString(uid, "");
        }


        public static String getUserType(Context ctx) {
            return getSharedPreferences(ctx).getString(userType, "");
        }

        public static void clearUserName(Context ctx) {
            SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
            editor.clear(); //clear all stored data
            editor.apply();
        }
    }

