package com.iiplabs.dltavro.model.dto;

import com.opencsv.bean.CsvBindByPosition;
import lombok.Data;

@Data
@SuppressWarnings("serial")
public class IntakeCustomerRecordDto extends BaseCsvRecordDto {

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

}
