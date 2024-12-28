package com.iiplabs.dltavro.services;

import java.io.IOException;
import java.io.InputStream;

public interface IImportService {

    long importCsv(InputStream is) throws IOException;

}
