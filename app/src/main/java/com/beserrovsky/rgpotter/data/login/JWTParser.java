package com.beserrovsky.rgpotter.data.login;

import com.beserrovsky.rgpotter.data.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class JWTParser extends Parser<String> {

    public final String parse(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line).append("\n");
        }
        br.close();
        return sb.toString().replaceAll("^\"|\"$", "");
    }
}
