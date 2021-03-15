package com.botnik.unity;

import android.util.Log;

import com.unity3d.player.UnityPlayer;

import com.github.pwittchen.neurosky.library.NeuroSky;
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException;
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener;
import com.github.pwittchen.neurosky.library.Preconditions;
import com.github.pwittchen.neurosky.library.message.enums.BrainWave;
import com.github.pwittchen.neurosky.library.message.enums.Signal;
import com.github.pwittchen.neurosky.library.message.enums.State;

import java.util.Set;

public class MyPlugin {

    private static final MyPlugin instance = new MyPlugin();

    private NeuroSky neuroSky;

    private static final String LOGTAG = "TGConnection";

    private static MyPlugin getInstance() {
        return instance;
    }

        private MyPlugin() {
        Log.i(LOGTAG, "Created Plugin");
        neuroSky = new NeuroSky(new ExtendedDeviceMessageListener() {
            @Override
            public void onStateChange(State state) {
                UnityPlayer.UnitySendMessage("ThinkGear", "OnStateChangeCall",Util.convertStateToJSONString(state));
            }

            @Override
            public void onSignalChange(Signal signal) {
                UnityPlayer.UnitySendMessage("ThinkGear", "OnSignalChangeCall",Util.convertSignalToJSONString(signal));
            }

            @Override
            public void onBrainWavesChange(Set<BrainWave> brainWaves) {
                UnityPlayer.UnitySendMessage("ThinkGear", "OnBrainWavesChangeCall",Util.convertBrainWavesToJSONString(brainWaves));
            }
        });
        try {
            neuroSky.connect();
        } catch (BluetoothNotEnabledException e) {
            Log.i(LOGTAG, "Problem.");
        }
    }

    public void startMonitoring() {
        if (Preconditions.isConnected(neuroSky.getDevice())) {
            neuroSky.startMonitoring();
            Log.i(LOGTAG, "START MONITORING");
        } else {
            Log.i(LOGTAG, "NOT CONNECTED");
            neuroSky.connect();
        }
    }
}