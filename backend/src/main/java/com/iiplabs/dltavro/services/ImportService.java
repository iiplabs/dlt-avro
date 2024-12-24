package com.iiplabs.dltavro.services;

import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service("importService")
public class ImportService implements IImportService {

    @Override
    public long importCsv(InputStream is) {
        return 0;
    }

}
