package com.iiplabs.dltavro.services;

import com.iiplabs.dltavro.TestApplicationContextInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ImportServiceTest {

    private final IImportService importService;

    @Autowired
    public ImportServiceTest(IImportService importService){
        this.importService = importService;
    }

    @Test
    void testImportCsv() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file","test_customers.csv",
                MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.readAllBytes(Paths
                .get(getClass().getClassLoader().getResource("test_customers.csv").toURI())));
        assertEquals(0, importService.importCsv(file.getInputStream()));
    }

}
