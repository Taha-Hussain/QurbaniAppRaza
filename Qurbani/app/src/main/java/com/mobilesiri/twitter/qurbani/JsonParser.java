package com.mobilesiri.twitter.qurbani;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Raza on 7/31/2016.
 */
public class JsonParser {
    public List<HashMap<String, Object>> parse(JSONObject jObject) {

        JSONArray jdetails = null;
        try {
            jdetails = jObject.getJSONArray("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDetails(jdetails);
    }

    private List<HashMap<String, Object>> getDetails(JSONArray jCountries) {
        int countryCount = jCountries.length();
        List<HashMap<String, Object>> List = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> values = null;

        for (int i = 0; i < countryCount; i++) {
            try {
                values = getValues((JSONObject) jCountries.get(i));
                List.add(values);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return List;
    }

    private HashMap<String, Object> getValues(JSONObject jValues) {
        HashMap<String, Object> val = new HashMap<String, Object>();
        String id = "";
        String name = "";
        String email = "";
        String gender = "";
        String address = "";

        String mobile = "";
        String home = "";
        String office= "";
        try {
            id = jValues.getString("id");
            name = jValues.getString("center_name");
            email = jValues.getString("status");


            String details = name;
            val.put("details", details);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return val;
    }
}
