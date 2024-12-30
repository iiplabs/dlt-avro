package com.iiplabs.dltavro.model.dto;

import com.opencsv.bean.CsvBindByPosition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Data
@SuppressWarnings("serial")
public class IntakeCustomerRecordDto extends BaseCsvRecordDto {

    @NotBlank(message = "{validation.invalid_sin_number}")
    @Pattern(regexp="\\d{3}-?\\d{3}-?\\d{3}", message = "{validation.invalid_sin_number}")
    @CsvBindByPosition(position = 0)
    private String sinNumber;

    @CsvBindByPosition(position = 1)
    private String firstName;

    @CsvBindByPosition(position = 2)
    private String lastName;

    @CsvBindByPosition(position = 3)
    private String phone;

    @CsvBindByPosition(position = 4)
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
