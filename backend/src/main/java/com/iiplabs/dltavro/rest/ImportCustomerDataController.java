package com.iiplabs.dltavro.rest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import com.iiplabs.dltavro.services.IImportService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iiplabs.dltavro.exceptions.InvalidContentException;
import com.iiplabs.dltavro.model.dto.ImportCustomerDataResponseDto;

@RequestMapping("/api/v1")
@RestController
@RestControllerAnnotation
public class ImportCustomerDataController {

    private static final Collection<String> ACCEPTABLE_CONTENT_TYPES = Arrays.asList("text/csv", "application/octet-stream", "application/vnd.ms-excel");

    private IImportService importService;

    public ImportCustomerDataController(@Qualifier("importService") IImportService importService) {
        this.importService = importService;
    }

    @PostMapping(path = "/import-customer-data", consumes = { MediaType.ALL_VALUE })
    public ResponseEntity<Object> importCustomerData(@RequestPart MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new InvalidContentException("Empty file provided");
        }

        String contentType = file.getContentType();
        if (!ACCEPTABLE_CONTENT_TYPES.contains(contentType)) {
            String message = String.format("Found content type %s of file %s. Expected types: %s",
                    contentType, file.getOriginalFilename(), ACCEPTABLE_CONTENT_TYPES);
            throw new InvalidContentException(message);
        }

        long numRecords = importService.importCsv(file.getInputStream());

        return ResponseEntity.ok(new ImportCustomerDataResponseDto(numRecords));
    }

}
