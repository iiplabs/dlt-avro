package com.iiplabs.dltavro.model.dto;

import com.opencsv.bean.CsvCustomBindByPosition;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@SuppressWarnings("serial")
public class IntakeCustomerRecordDto extends BaseCsvRecordDto {

    private final static String PROFILE_DEFAULT = "default";

    @NotBlank(message = "{validation.invalid_sin_number}")
    @Pattern(regexp="\\d{3}-?\\d{3}-?\\d{3}", message = "{validation.invalid_sin_number}")
    @CsvCustomBindByPosition(position = 0, required = true, profiles = {PROFILE_DEFAULT}, converter = TrimCustomCsvBeanConverter.class)
    private String sinNumber;

    @CsvCustomBindByPosition(position = 1, required = true, profiles = {PROFILE_DEFAULT}, converter = TrimCustomCsvBeanConverter.class)
    private String firstName;

    @CsvCustomBindByPosition(position = 2, required = true, profiles = {PROFILE_DEFAULT}, converter = TrimCustomCsvBeanConverter.class)
    private String lastName;

    @CsvCustomBindByPosition(position = 3, required = true, profiles = {PROFILE_DEFAULT}, converter = TrimCustomCsvBeanConverter.class)
    private String phone;

    @NotBlank(message = "{validation.invalid_email}")
    @Email(message = "{validation.invalid_email}")
    @CsvCustomBindByPosition(position = 4, required = true, profiles = {PROFILE_DEFAULT}, converter = TrimCustomCsvBeanConverter.class)
    private String email;

    private UUID batchId;

    private long order;

    private Date processDate;

    private UUID uuid;

    private Collection<String> validationErrors;

    public boolean isValid() {
        return validationErrors.isEmpty();
    }

}
