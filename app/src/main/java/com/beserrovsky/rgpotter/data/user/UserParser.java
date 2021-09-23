package com.beserrovsky.rgpotter.data.user;

import com.beserrovsky.rgpotter.data.Parser;
import com.beserrovsky.rgpotter.models.UserModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class UserParser extends Parser<UserModel> {
    @Override
    public UserModel parse(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return new Gson().fromJson(sb.toString(), UserModel.class);
    }

    public String parse(UserModel user){
        return "{ " +
                "email: \"" + user.email + "\", " +
                "name: \"" + user.name + "\", " +
                "house_Id: \"" + user.house_Id + "\", " +
                "pronoum: \"" + user.pronoum + "\", " +
                "password: \"" + user.Password + "\", " +
                "lumusSuccesses: \"" + user.lumusSuccess + "\", " +
                "lumusFails: \"" + user.lumusFails + "\" " +
                "}";
    }

}
