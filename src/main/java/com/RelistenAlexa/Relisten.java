package com.RelistenAlexa;

import com.RelistenAlexa.Handlers.LaunchRequestHandler;
import com.google.gson.*;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Relisten {

    public static void main(String[] args) throws Exception {

    }

    /**
     * Returns JsonElement given a URL string
     */
    public static JsonElement jsonElementFromURL(String urlString) throws Exception {
        // Creates URL from string and opens connection
        URL url = new URL(urlString);
        URLConnection request = url.openConnection();
        request.connect();

        // Generates a parse tree from JSON
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonTree = jsonParser.parse(new InputStreamReader((InputStream) request.getContent()));
        return jsonTree;
    }

    /**
     * Returns a jsonArray if jsonElement is a jsonArray
     */
    public static JsonArray jsonArrayFromElement(JsonElement jsonElement) {
        if(jsonElement.isJsonArray()){
            return jsonElement.getAsJsonArray();
        }
        // TODO add alert if not a jsonArray
        else {
            System.out.println("jsonTree is not a JsonArray");
            return null;}
    }

    // TODO - handle BOTH website down and show not found
    public static JsonObject jsonObjectFromURL(String urlString) throws Exception {
        JsonElement jsonElement;
        try {
            jsonElement = jsonElementFromURL(urlString);
        }
        catch (Exception e) {
            System.out.println("Fix me, bucko.");
            return null;
        }
        return jsonElement.getAsJsonObject();
    }

    /**
     * Prints all band names and their slugs. Only used by developers to update slots in Alexa Developer Console.
     */
    public static void printBandNames() {
        String urlString = "https://api.relisten.net/api/v2/artists/";
        JsonArray jsonArray;
        try {
            jsonArray = jsonArrayFromElement(jsonElementFromURL(urlString));
        }
        catch (Exception e) {
            System.out.println("Is bronk :("); // TODO - handle this
            return;
        }


        for (int i = 0; i < jsonArray.size(); i++) {
            JsonObject jsonObject = (JsonObject) jsonArray.get(i);
            String name = jsonObject.get("name").toString();
            String slug = jsonObject.get("slug").toString();
            name = name.replaceAll("\"","");
            slug = slug.replaceAll("\"","");
            //System.out.print(jsonObject.get("name") + ", " + jsonObject.get("slug"));
            System.out.println(name + "," + slug);
        }
    }

}
