package com.TwinBlade.QSCP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Main extends PreferenceActivity implements OnPreferenceChangeListener {   
	
	private Preference pSettings, pBrightness, pSeekbar, pBattery, pRotation, pAirplane, pWifi, pData, pBT, pScreen, pRinger, pLocation, pWifiAP, pTorch, pAlarm;
	
	private final String SETTINGS = "QSM_SETTINGS";
	private final String SEEKBAR = "QSM_SEEKBAR";
	private final String BATTERY = "QSM_BATTERY";
	private final String ROTATION = "QSM_ROTATION";
	private final String AIRPLANE = "QSM_AIRPLANE";
	private final String WIFI = "QSM_WIFI";
	private final String WIFI_AP = "QSM_WIFI_AP";
	private final String DATA = "QSM_DATA";
	private final String BT = "QSM_BT";
	private final String SCREEN = "QSM_SCREEN";
	private final String LOCATION = "QSM_LOCATION";
	private final String RINGER = "QSM_RINGER";
	private final String TORCH = "QSM_TORCH";
	private final String BRIGHTNESS = "QSM_BRIGHTNESS";
	private final String ALARM = "QSM_ALARM";
	private final String APPLY = "Apply";
	private final String DONATE = "Donate";
	private final String SETTINGS_KEY = "QSM_ENABLED_TILES";
	private final String COLUMNS = "QSM_TILES_COLUMNS";
	private final String AUTO_DEFAULT = "QSM_AUTO_DEFAULT";
    private final String EDGE_TRIGGER = "QSM_EDGE_TRIGGER";
	
	private List<String> mTilesList = Arrays.asList(SETTINGS, BRIGHTNESS, SEEKBAR, BATTERY, ROTATION, AIRPLANE, WIFI, DATA, BT, SCREEN, RINGER, 
				LOCATION, WIFI_AP, TORCH, ALARM);
	
	private ArrayList<String> mTilesOrderedList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        loadSettings();
        addPreferencesFromResource(R.xml.main);

        pSettings = findPreference(SETTINGS);
        pBrightness = findPreference(BRIGHTNESS);
        pSeekbar = findPreference(SEEKBAR);
        pBattery = findPreference(BATTERY);
        pRotation = findPreference(ROTATION);
        pAirplane = findPreference(AIRPLANE);
        pWifi = findPreference(WIFI);
        pData = findPreference(DATA);
        pBT = findPreference(BT);
        pScreen = findPreference(SCREEN);
        pRinger = findPreference(RINGER);
        pLocation = findPreference(LOCATION);
        pWifiAP = findPreference(WIFI_AP);
        pTorch = findPreference(TORCH);
        pAlarm = findPreference(ALARM);
        
        pSettings.setOnPreferenceChangeListener(this);
        pBrightness.setOnPreferenceChangeListener(this);
        pSeekbar.setOnPreferenceChangeListener(this);
        pBattery.setOnPreferenceChangeListener(this);
        pRotation.setOnPreferenceChangeListener(this);
        pAirplane.setOnPreferenceChangeListener(this);
        pWifi.setOnPreferenceChangeListener(this);
        pData.setOnPreferenceChangeListener(this);
        pBT.setOnPreferenceChangeListener(this);
        pScreen.setOnPreferenceChangeListener(this);
        pRinger.setOnPreferenceChangeListener(this);
        pLocation.setOnPreferenceChangeListener(this);
        pWifiAP.setOnPreferenceChangeListener(this);
        pTorch.setOnPreferenceChangeListener(this);
        pAlarm.setOnPreferenceChangeListener(this);
        
        Preference pApply = findPreference(APPLY);
        pApply.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				
				writeSettings();
				
				try {
		            Runtime.getRuntime().exec(new String[] {"su", "-c", "pkill com.android.systemui"});
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				try {
		            Runtime.getRuntime().exec(new String[] {"am startservice -n com.android.systemui/.SystemUIService"});
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
				
				Toast.makeText(getBaseContext(), "Settings Applied. Restarting SystemUI.", Toast.LENGTH_SHORT).show();
				
				return true;
			}      	
        });
    }
    
    private void loadSettings() {
    	
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String tilesListString = Settings.System.getString(getContentResolver(), SETTINGS_KEY);
        if (tilesListString != null) {
        	List<String> tmpList = Arrays.asList(tilesListString.split(";"));
        	for (String t : tmpList) {
        		mTilesOrderedList.add(t);
        	}
            
            for (String tile : mTilesList) {	
        		mSharedPreferences.edit().putBoolean(tile, mTilesOrderedList.contains(tile)).commit();    
        	}
        } else {
        	mTilesOrderedList.add(SETTINGS);
        	mTilesOrderedList.add(ALARM);
        }
        
        mSharedPreferences.edit().putBoolean(COLUMNS, Settings.System.getInt(getContentResolver(), COLUMNS, 4) == 3);
        mSharedPreferences.edit().putBoolean(AUTO_DEFAULT, Settings.System.getInt(getContentResolver(), AUTO_DEFAULT, 1) == 1);
        mSharedPreferences.edit().putBoolean(EDGE_TRIGGER, Settings.System.getInt(getContentResolver(), EDGE_TRIGGER, 1) == 1);
    }
    
    private void writeSettings() { 
    	StringBuilder stringBuilder = new StringBuilder();
    	for (String tile : mTilesOrderedList) {
    		stringBuilder.append(tile);
			stringBuilder.append(";");
    	}
    	Settings.System.putString(getContentResolver(), SETTINGS_KEY, stringBuilder.toString());
    	
    	SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    	Settings.System.putInt(getContentResolver(), COLUMNS, mSharedPreferences.getBoolean(COLUMNS, true) ? 3 : 4);
    	Settings.System.putInt(getContentResolver(), AUTO_DEFAULT, mSharedPreferences.getBoolean(AUTO_DEFAULT, true) ? 1 : 0);
    	Settings.System.putInt(getContentResolver(), EDGE_TRIGGER, mSharedPreferences.getBoolean(EDGE_TRIGGER, true) ? 1 : 0);
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		
		if ((Boolean) newValue) {
			mTilesOrderedList.add(preference.getKey().toString());
		} else {
			mTilesOrderedList.remove(preference.getKey().toString());
		}

		return true;
	}
}