package com.egles121.mngmtsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String jobTitle;
    private String departmentName;
    private String startDate;
    private String endDate;
}
