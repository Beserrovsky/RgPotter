package com.beserrovsky.rgpotter.data.login;

import com.beserrovsky.rgpotter.data.RepositoryCallback;
import com.beserrovsky.rgpotter.data.Result;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;

public class LoginRepository {

    private final String loginUrl = "https://rgpotterapi.herokuapp.com/api/user/login";

    private final JWTParser responseParser;
    private final Executor executor;

    public LoginRepository(JWTParser responseParser, Executor executor) {
        this.responseParser = responseParser;
        this.executor = executor;
    }

    public void makeLoginRequest(final String jsonBody, final RepositoryCallback<String> callback) {
        executor.execute(() -> {
            try {
                Result<String> result = makeSynchronousLoginRequest(jsonBody);
                callback.onComplete(result);
            } catch (Exception e) {
                Result<String> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    private Result<String> makeSynchronousLoginRequest(String jsonBody) {
        try {
            URL url = new URL(loginUrl);
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");
            httpConnection.setDoOutput(true);
            httpConnection.getOutputStream().write(jsonBody.getBytes(StandardCharsets.UTF_8));

            String loginResponse = responseParser.parse(httpConnection.getInputStream());
            return new Result.Success<>(loginResponse);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }

}
