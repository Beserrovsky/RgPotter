package com.beserrovsky.rgpotter.data.character;

import com.beserrovsky.rgpotter.data.RepositoryCallback;
import com.beserrovsky.rgpotter.data.Result;
import com.beserrovsky.rgpotter.models.CharacterModel;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;

public class CharacterRepository {

    private final String characterUrl = "https://harry-potter-deploy.herokuapp.com/api/1/characters/all";

    private final CharacterParser characterParser;
    private final Executor executor;

    public CharacterRepository (CharacterParser parser, Executor executor) {
        this.characterParser = parser;
        this.executor = executor;
    }

    public void makeGetRequest(final RepositoryCallback<CharacterModel[]> callback) {
        executor.execute(() -> {
            try {
                Result<CharacterModel[]> result = makeSynchronousGetRequest();
                callback.onComplete(result);
            } catch (Exception e) {
                Result<CharacterModel[]> errorResult = new Result.Error<>(e);
                callback.onComplete(errorResult);
            }
        });
    }

    private Result<CharacterModel[]> makeSynchronousGetRequest() {
        try {
            URL url = new URL(characterUrl);

            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            httpConnection.setRequestProperty("Accept", "application/json");

            CharacterModel[] postResponse = characterParser.parse(httpConnection.getInputStream());
            return new Result.Success<>(postResponse);
        } catch (Exception e) {
            return new Result.Error<>(e);
        }
    }
}
