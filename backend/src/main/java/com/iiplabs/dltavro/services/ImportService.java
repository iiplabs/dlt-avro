package com.iiplabs.dltavro.services;

import com.iiplabs.dltavro.model.dto.BaseCsvRecordDto;
import com.iiplabs.dltavro.model.dto.IntakeCustomerRecordDto;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service("importService")
public class ImportService implements IImportService {

    @Override
    public long importCsv(InputStream is) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            CsvToBean<BaseCsvRecordDto> rawCustomerRecords = new CsvToBeanBuilder<BaseCsvRecordDto>(fileReader)
                    .withType(IntakeCustomerRecordDto.class)
                    .withIgnoreEmptyLine(true)
                    .withSeparator('|')
                    .withThrowExceptions(true)
                    .build();
            return rawCustomerRecords.parse().stream().count();
        }
    }

}
