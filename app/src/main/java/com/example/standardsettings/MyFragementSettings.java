package com.example.standardsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreference;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class MyFragementSettings extends PreferenceFragmentCompat {

    private Context context;//for passing context of fragment
    private PreferenceScreen screen;
    private SharedPreferences preferences;//for getting preferences
    private PreferenceCategory displayCategory;//the orientation display section
    private ListPreference listOrientation;//for choosing orientation
    public static final String ORIENTATION = "orientation";
    public static final String THEME = "theme";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        initFields();
        loadFields();
        makeFields();
        setThemes();//For APIs below 29

        setPreferenceScreen(screen);
    }



    private void initFields() {
        this.context = getPreferenceManager().getContext();
        this.screen = getPreferenceManager().createPreferenceScreen(this.context);
        this.preferences = getDefaultSharedPreferences(context);
        listOrientation = new ListPreference(context);
        listOrientation.setKey(ORIENTATION);
    }


    private void loadFields() {
        if (preferences.contains(ORIENTATION)){
            String orientation = preferences.getString(ORIENTATION, "");
            switch (orientation){
                case "1"://unspecified
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                    listOrientation.setSummary("Unspecified");
                    break;
                case "2"://portrait
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    listOrientation.setSummary("Portrait");
                    break;
                case "3"://landscape
                    getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    listOrientation.setSummary("Landscape");
                    break;
            }

        }

    }

    private void makeFields() {
        //listOrientation
        listOrientation.setTitle("Orientation");
        listOrientation.setEntries(R.array.entries);
        listOrientation.setEntryValues(R.array.values);
        listOrientation.setDialogTitle("Choose orientation:");
        listOrientation.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switch (newValue.toString()){
                    case "1"://unspecified
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                        listOrientation.setSummary("Unspecified");
                        break;
                    case "2"://portrait
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        listOrientation.setSummary("Portrait");
                        break;
                    case "3"://landscape
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        listOrientation.setSummary("Landscape");
                        break;
                }
                return true;
            }
        });
        //listOrientation
        //displayCategory
        displayCategory = new PreferenceCategory(context);
        displayCategory.setKey("displayCategory");
        displayCategory.setTitle("Display");
        screen.addPreference(displayCategory);
        displayCategory.addPreference(listOrientation);
        //displayCategory
    }


    private void setThemes() {
        if (Build.VERSION.SDK_INT < 29){
            SwitchPreference switchTheme = new SwitchPreference(context);
            switchTheme.setKey(THEME);

            if (preferences.contains(THEME)){
                boolean isDark = preferences.getBoolean(THEME, false);
                if (isDark){
                  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
            //switchTheme
            switchTheme.setSummaryOff("The theme is light.");
            switchTheme.setSummaryOn("The theme is dark.");
            switchTheme.setTitle("Dark Mode");
            switchTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    boolean isDark = (boolean) newValue;
                    if (isDark){
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    return true;
                }
            });
            //switchTheme
            //themeCategory
            PreferenceCategory themeCategort = new PreferenceCategory(context);
            themeCategort.setKey("themeCategort");
            themeCategort.setTitle("Theme");
            screen.addPreference(themeCategort);
            themeCategort.addPreference(switchTheme);
            //themeCategory
        }
    }
}
