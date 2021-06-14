package com.example.rg_potter.data;

import android.content.Context;
import android.util.Log;

import com.example.rg_potter.entity.Character;
import com.example.rg_potter.entity.User;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

final public class Global {

    private Global(){} // Non-instantiating Class


    // Global constraints

    public static final String API_URL = "https://harry-potter-deploy.herokuapp.com";
    public static final String CHARACTERS_ENDPOINT = "/api/1/characters/all";
    public static final String LOCAL_JSON = "characters.json";

    // Global vars

    public static boolean SAFE_INSTALL = true;

    // Global Objects

    public static Character[] characters;
    public static User user;

    // Global Methods

    public static void loadCharactersJson(Context ctx){
        Gson gson = new Gson();

        File file = new File(ctx.getFilesDir(), Global.LOCAL_JSON);

        FileInputStream fin = null;

        try {
            fin = new FileInputStream(file);

            String fileJson = convertStreamToString(fin);

            Log.d("JSON", "loaded: \n"+fileJson);

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
