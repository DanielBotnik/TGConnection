package com.botnik.unity;

import com.github.pwittchen.neurosky.library.message.enums.BrainWave;
import com.github.pwittchen.neurosky.library.message.enums.Signal;
import com.github.pwittchen.neurosky.library.message.enums.State;

import java.util.Set;

public class Util {
    public static String convertStateToJSONString(State state) {
        return String.format("{ \"type\": %d }",state.getType());
    }
    public static String convertSignalToJSONString(Signal signal) {
        return String.format("{ \"type\": %d , \"value\": %d }",signal.getType(),signal.getValue());
    }
    public static String convertBrainWaveToJSONString(BrainWave brainwave) {
        return String.format("{ \"type\": %d , \"value\": %d }",brainwave.getType(),brainwave.getValue());
    }

    public static String convertBrainWavesToJSONString(Set<BrainWave> brainWaves) {
        String JSON = "[";
        for(BrainWave brainWave : brainWaves) {
            JSON += convertBrainWaveToJSONString(brainWave) + ",";
        }
        return JSON.substring(0,JSON.length() - 1) + "]";
    }
}
