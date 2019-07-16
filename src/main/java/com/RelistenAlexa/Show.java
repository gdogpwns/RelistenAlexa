package com.RelistenAlexa;

import com.google.gson.*;

import java.util.ArrayList;

public class Show {
    // Filled when initialized
    String jsonURL;
    JsonObject showRoot;

    // Filled by updateShow()
    JsonArray sourcesArray;
    JsonObject currentSource;
    JsonArray setRootArray;
    // Defaults to the first source of a recorded show.
    String bandName;
    String showDate;
    String avgShowRating;
    int maxSetIndex;
    int currentSetIndex = 0; // TODO - allow changing of initial set
    ArrayList<Set> setArrayList = new ArrayList<>();
    Set currentSet;


    public Show(String jsonURL) throws Exception {
        this.jsonURL = jsonURL;
        this.showRoot = Relisten.jsonObjectFromURL(jsonURL);
        sourcesArray = showRoot.get("sources").getAsJsonArray();
        currentSource = sourcesArray.get(0).getAsJsonObject(); // TODO - allow changing of source
        setRootArray = currentSource.get("sets").getAsJsonArray();
        showDate = showRoot.get("display_date").getAsString();
        avgShowRating = showRoot.get("avg_rating").getAsString();
        buildSets();
        currentSet = setArrayList.get(currentSetIndex); // TODO - allow changing of initial set
    }

    private void buildSets() {
        setArrayList.clear();

        for (int i = 0; i < setRootArray.size(); i++) {
            Set set = new Set(setRootArray.get(i).getAsJsonObject());
            setArrayList.add(set);
        }
        maxSetIndex = setRootArray.size();
    }

    // Must be called separately from updateShow() since bandName does not exist in JSON
    public void updateBandName(String bandName) {
        this.bandName = bandName;
    }

    public void changeSet(int newSetIndex) {
        currentSetIndex = newSetIndex;
        currentSet = setArrayList.get(currentSetIndex);
    }

    public void goPreviousSet() {
        if (currentSetIndex > 0) {
            changeSet(currentSetIndex - 1);
        }
        else {
            System.out.println("You are on the earliest set."); // TODO - handle this
        }
    }

    public void goForwardSet() {
        if (currentSetIndex < maxSetIndex) {
            changeSet(currentSetIndex + 1);
        }
        else {
            System.out.println("You are on the farthest set."); // TODO - handle this
        }
    }
}
