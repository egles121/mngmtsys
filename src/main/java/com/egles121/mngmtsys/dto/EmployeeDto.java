package com.egles121.mngmtsys.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.Instant;

@Component
@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String jobTitle;
    private String departmentName;
    private Instant startDate;
    private Instant endDate;
    private boolean terminated = false;

    @ModelAttribute("employee")
    public EmployeeDto employeeDto() {
        return new EmployeeDto();
    }
}
