package com.beserrovsky.rgpotter.data;

import com.beserrovsky.rgpotter.data.Result;

public interface RepositoryCallback<T> {
    void onComplete(Result<T> result);
}
