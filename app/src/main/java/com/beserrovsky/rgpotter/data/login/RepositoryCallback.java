package com.beserrovsky.rgpotter.data.login;

import com.beserrovsky.rgpotter.data.Result;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
