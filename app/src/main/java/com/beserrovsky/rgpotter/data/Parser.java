package com.beserrovsky.rgpotter.data;

import java.io.IOException;
import java.io.InputStream;

public abstract class Parser<T> {
    public abstract T parse(InputStream inputStream) throws IOException;
}
