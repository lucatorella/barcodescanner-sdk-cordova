package com.mirasense.scanditsdk.plugin;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.RelativeLayout;

import com.scandit.barcodepicker.BarcodePicker;
import com.scandit.base.system.SbSystemUtils;

import java.util.List;

/**
 * Created by mo on 14/12/15.
 */
public class PhonegapParamParser {

    public static final String paramSearchBar = "searchBar".toLowerCase();
    public static final String paramSearchBarPlaceholderText = "searchBarPlaceholderText".toLowerCase();
    public static final String paramMinSearchBarBarcodeLength = "minSearchBarBarcodeLength".toLowerCase();
    public static final String paramMaxSearchBarBarcodeLength = "maxSearchBarBarcodeLength".toLowerCase();

    public static final String paramPortraitMargins = "portraitMargins".toLowerCase();
    public static final String paramLandscapeMargins = "landscapeMargins".toLowerCase();
    public static final String paramAnimationDuration = "animationDuration".toLowerCase();

    public static final String paramContinuousMode = "continuousMode".toLowerCase();
    public static final String paramPaused = "paused".toLowerCase();


    public static void updatePicker(SearchBarBarcodePicker picker, Bundle bundle,
                                    SearchBarBarcodePicker.ScanditSDKSearchBarListener listener) {
        if (bundle.containsKey(paramSearchBar)) {
            picker.showSearchBar(bundle.getBoolean(paramSearchBar));
            picker.setOnSearchBarListener(listener);
        }

        if (bundle.containsKey(paramSearchBarPlaceholderText)) {
            picker.setSearchBarPlaceholderText(bundle.getString(paramSearchBarPlaceholderText));
        }

        if (bundle.containsKey(paramMinSearchBarBarcodeLength)) {

        }

        if (bundle.containsKey(paramMaxSearchBarBarcodeLength)) {

        }
    }

    public static void updateLayout(final Activity activity, final SearchBarBarcodePicker picker,
                                    Bundle bundle) {
        
        if (picker == null || bundle == null) {
            return;
        }
        
        double animationDuration = 0;
        if (bundle.containsKey(paramAnimationDuration)) {
            animationDuration = bundle.getDouble(paramAnimationDuration);
        }

        if (bundle.containsKey(paramPortraitMargins) || bundle.containsKey(paramLandscapeMargins)) {
            Rect portraitMargins = new Rect(0, 0, 0, 0);
            Rect landscapeMargins = new Rect(0, 0, 0, 0);

            if (bundle.containsKey(paramPortraitMargins)) {
                if (bundle.getSerializable(paramPortraitMargins) != null
                        && bundle.getSerializable(paramPortraitMargins) instanceof List) {
                    List<Object> list = (List<Object>) bundle.getSerializable(paramPortraitMargins);
                    if (list.size() == 4 &&
                                    (UIParamParser.checkClassOfListObjects(list, Integer.class) ||
                                     UIParamParser.checkClassOfListObjects(list, String.class))) {
                        portraitMargins = new Rect(
                                        UIParamParser.getSize(list.get(0), ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(list.get(1), ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(list.get(2), ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(list.get(3), ScanditSDK.SCREEN_HEIGHT));
                    }
                } else if (bundle.getString(paramPortraitMargins) != null) {
                    String portraitMarginsString = bundle.getString(paramPortraitMargins);
                    String[] split = portraitMarginsString.split("[/]");
                    if (split.length == 4) {
                        try {
                            portraitMargins = new Rect(
                                        UIParamParser.getSize(split[0], ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(split[1], ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(split[2], ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(split[3], ScanditSDK.SCREEN_HEIGHT));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.e("ScanditSDK", "Failed to parse '" + paramPortraitMargins + "' - wrong type.");
                }
            }

            if (bundle.containsKey(paramLandscapeMargins)) {
                if (bundle.getSerializable(paramLandscapeMargins) != null
                        && bundle.getSerializable(paramLandscapeMargins) instanceof List) {
                    List<Object> list = (List<Object>) bundle.getSerializable(paramLandscapeMargins);
                    if (list.size() == 4 &&
                            (UIParamParser.checkClassOfListObjects(list, Integer.class) ||
                             UIParamParser.checkClassOfListObjects(list, String.class))) {
                        landscapeMargins = new Rect(
                                        UIParamParser.getSize(list.get(0), ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(list.get(1), ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(list.get(2), ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(list.get(3), ScanditSDK.SCREEN_WIDTH));
                    }
                } else if (bundle.getString(paramLandscapeMargins) != null) {
                    String landscapeMarginsString = bundle.getString(paramLandscapeMargins);
                    String[] split = landscapeMarginsString.split("[/]");
                    if (split.length == 4) {
                        try {
                            landscapeMargins = new Rect(
                                        UIParamParser.getSize(split[0], ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(split[1], ScanditSDK.SCREEN_WIDTH),
                                        UIParamParser.getSize(split[2], ScanditSDK.SCREEN_HEIGHT),
                                        UIParamParser.getSize(split[3], ScanditSDK.SCREEN_WIDTH));
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Log.e("ScanditSDK", "Failed to parse '" + paramLandscapeMargins + "' - wrong type.");
                }
            }

            picker.adjustSize(activity, portraitMargins, landscapeMargins, animationDuration);
        }
    }
}
