package com.RelistenAlexa;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class Set {
    JsonObject setObj;
    int setIndex = 0;
    int trackIndex = 0;
    ArrayList<Track> trackArrayList = new ArrayList<>();
    Track currentTrack;
    Boolean isEncore;
    Boolean hasNextTrack = true;


    // TODO - allow starting a show at a certain set
    public Set(JsonObject setObj) {
        this.setObj = setObj;
        setIndex = setObj.get("index").getAsInt();
        isEncore = setObj.get("is_encore").getAsBoolean();
        buildTracks();
        currentTrack = trackArrayList.get(trackIndex);
        updateHasNextTrack();
    }


    public void buildTracks() {
        JsonArray trackObjArray = setObj.get("tracks").getAsJsonArray();
        for (int j = 0; j < trackObjArray.size(); j++) {
            JsonObject trackObj = trackObjArray.get(j).getAsJsonObject();
            Track track = new Track(trackObj);
            trackArrayList.add(track);
        }

    }

    public void updateHasNextTrack() {
        if (trackIndex == trackArrayList.size()) {
            hasNextTrack = false;
        }
    }

    public void updateCurrentTrack() {
        if (hasNextTrack) {
            trackIndex += 1;
            currentTrack = trackArrayList.get(trackIndex);
        }
    }

    // TODO - Remember about double encores. When making a while to find the encore, stop upon first.
}
