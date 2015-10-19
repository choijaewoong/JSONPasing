package com.torerov.melonapi;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tacademy on 2015-10-19.
 */
public class Songs implements JSONParsing {

    @SerializedName("song")
    List<Song> songList;


    @Override
    public void parsing(JSONObject jsonObject) throws JSONException {
        songList = new ArrayList<Song>();
        JSONArray array = jsonObject.getJSONArray("songList");
        for (int i = 0; i < array.length(); i++) {
            JSONObject jsong = array.getJSONObject(i);
            Song s = new Song();
            s.parsing(jsong);
            songList.add(s);
        }
    }
}
