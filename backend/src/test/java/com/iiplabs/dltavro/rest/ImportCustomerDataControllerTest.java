package com.iiplabs.dltavro.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@ActiveProfiles("test")
@ContextConfiguration(initializers = TestApplicationContextInitializer.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImportCustomerDataControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ImportCustomerDataController importCustomerDataController;

    @BeforeAll
    public static void setup() {
        //
    }

    @Test
    void contextLoads() {
        //
    }

    @Test
    void whenImportCustomerDataControllerInjected_thenNotNull() throws Exception {
        assertThat(importCustomerDataController).isNotNull();
    }

    @Test
    void testImportGoodFile() throws Exception {
        // read file from the classpath
        MockMultipartFile file = new MockMultipartFile("file","test_customers.csv",
                MediaType.APPLICATION_OCTET_STREAM_VALUE, Files.readAllBytes(Paths
                .get(getClass().getClassLoader().getResource("test_customers.csv").toURI())));

        mockMvc.perform(multipart("/api/v1/import-customer-data").file(file))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.numRecords").value(0));
    }

    @Test
    void testImportEmptyFile() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "".getBytes());

        mockMvc.perform(multipart("/api/v1/import-customer-data").file(file)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testImportWrongContentType() throws Exception {
        MockMultipartFile employeeJson = new MockMultipartFile("file", null,
                "application/json", "{\"name\": \"Emp Name\"}".getBytes());

        mockMvc.perform(multipart("/api/v1/import-customer-data").file(employeeJson)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testImportWrongMultipartName() throws Exception {
        MockMultipartFile file = new MockMultipartFile("notFile", "".getBytes());

        mockMvc.perform(multipart("/api/v1/import-customer-data").file(file)).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
