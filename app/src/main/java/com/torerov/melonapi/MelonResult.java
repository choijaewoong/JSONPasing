package com.torerov.melonapi;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Tacademy on 2015-10-19.
 */
public class MelonResult implements JSONParsing{

    Melon melon;


    @Override
    public void parsing(JSONObject jsonObject) throws JSONException {
        melon =  new Melon();
        JSONObject jmelon = jsonObject.getJSONObject("melon");
        melon.parsing(jmelon);
    }
}
