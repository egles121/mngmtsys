package com.egles121.mngmtsys.dto.mapping;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.exception.ManagementAppException;
import com.egles121.mngmtsys.model.Employee;
import com.egles121.mngmtsys.repositories.DepartmentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static com.egles121.mngmtsys.constants.Constants.DEPARTMENT_EMPTY;
import static com.egles121.mngmtsys.constants.Constants.DEPARTMENT_NOT_FOUND;
import static com.egles121.mngmtsys.constants.Constants.PATTERN_FORMAT;

@Component
public class EmployeeMapper {

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeDto mapEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setJobTitle(employee.getJobTitle());
        employeeDto.setDepartmentName(employee.getDepartment().getName());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

        employeeDto.setStartDate(formatter.format(employee.getStartDate()));
        if (Objects.nonNull(employee.getEndDate())) {
            employeeDto.setEndDate(formatter.format(employee.getEndDate()));
        }
        return employeeDto;
    }

    public Employee mapEmployee(EmployeeDto employeeDto) {
        if (StringUtils.isEmpty(employeeDto.getDepartmentName())) {
            throw new ManagementAppException(DEPARTMENT_EMPTY);
        }
        if (departmentRepository.findByName(employeeDto.getDepartmentName()) == null) {
            throw new ManagementAppException(DEPARTMENT_NOT_FOUND);
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setJobTitle(employeeDto.getJobTitle());
        employee.setDepartment(departmentRepository.findByName(employeeDto.getDepartmentName()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                .withZone(ZoneId.systemDefault());
        employee.setStartDate(Instant.from(formatter.parse(employeeDto.getStartDate())));
        if (Objects.nonNull(employeeDto.getEndDate())) {
            employee.setEndDate(Instant.from(formatter.parse(employeeDto.getEndDate())));
        }
        return employee;
    }
}
