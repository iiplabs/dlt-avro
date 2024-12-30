package com.iiplabs.dltavro.services;

import com.iiplabs.dltavro.model.dto.BaseCsvRecordDto;
import com.iiplabs.dltavro.model.dto.IntakeCustomerRecordDto;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvChainedException;
import com.opencsv.exceptions.CsvFieldAssignmentException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("importService")
public class ImportService implements IImportService {

    private Validator validator;

    public ImportService(@Qualifier("validator") Validator validator) {
        this.validator = validator;
    }

    @Override
    public long importCsv(InputStream is) throws IOException {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            CsvToBean<BaseCsvRecordDto> rawCustomerRecords = new CsvToBeanBuilder<BaseCsvRecordDto>(fileReader)
                    .withType(IntakeCustomerRecordDto.class)
                    .withIgnoreEmptyLine(true)
                    .withSeparator('|')
                    // .withMappingStrategy(createMappingStrategy())
                    .withThrowExceptions(false)
                    .build();

            Collection<BaseCsvRecordDto> customerRecords = rawCustomerRecords.parse();

            UUID batchId = UUID.randomUUID();
            // send an event to persist batchId

            long order = 0;
            for (BaseCsvRecordDto customerRecord : customerRecords) {
                ((IntakeCustomerRecordDto) customerRecord).setBatchId(batchId);
                ((IntakeCustomerRecordDto) customerRecord).setOrder(order);
                ((IntakeCustomerRecordDto) customerRecord).setProcessDate(new Date());
                ((IntakeCustomerRecordDto) customerRecord).setUuid(UUID.randomUUID());
                ((IntakeCustomerRecordDto) customerRecord).setValidationErrors(validator
                        .validate(customerRecord).stream()
                        .map(ConstraintViolation::getMessage)
                        .collect(Collectors.toList()));
                order++;
            }

            Map<Boolean, List<BaseCsvRecordDto>> groupedByValidStatusMap = customerRecords.stream()
                    .collect(Collectors.groupingBy(customerRecord -> ((IntakeCustomerRecordDto) customerRecord).isValid()));

            return groupedByValidStatusMap.get(Boolean.TRUE).size();
        }
    }

    private <T> MappingStrategy<T> createMappingStrategy() {
        MappingStrategy<T> mappingStrategy = new ColumnPositionMappingStrategy<T>() {

            @Override
            public T populateNewBean(String[] line) throws CsvBeanIntrospectionException, CsvFieldAssignmentException, CsvChainedException {
                Arrays.setAll(line, (i) -> line[i].trim());
                return super.populateNewBean(line);
            }

        };
        return mappingStrategy;
    }

}
