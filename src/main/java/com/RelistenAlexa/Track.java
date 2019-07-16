package com.RelistenAlexa;

import com.google.gson.JsonObject;

public class Track {
    String title;
    String url;
    int trackPosition;


    public Track(JsonObject trackObj)
    {
        title = trackObj.get("title").getAsString();
        url = trackObj.get("mp3_url").getAsString();
        trackPosition = trackObj.get("track_position").getAsInt();
    }

}
