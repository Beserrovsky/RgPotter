package com.example.rg_potter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

final public class Global {

    // GLOBAL CONSTS

    public static final String API_URL = "https://harry-potter-deploy.herokuapp.com";
    public static final String CHARACTERS_ENDPOINT = "/api/1/characters/all";
    public static final String LOCAL_JSON = "characters.json";

    // GLOBAL VARS

    public static boolean SAFE_INSTALL = true;
    public static int user_index = 0;

    // Global Objects

    public static Character[] characters;
    public static User user;

    // Global methods

    public static void loadCharactersJson(Context ctx){
        Gson gson = new Gson();

        File file = new File(ctx.getFilesDir(), Global.LOCAL_JSON);

        FileInputStream fin = null;

        try {
            fin = new FileInputStream(file);

            String fileJson = convertStreamToString(fin);

            Log.d("JSON loaded: ", fileJson);

            fin.close();

            Global.characters = gson.fromJson(fileJson, Character[].class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}
