package com.egles121.mngmtsys.dto.mapping;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.exception.ManagementAppException;
import com.egles121.mngmtsys.model.Employee;
import com.egles121.mngmtsys.repositories.DepartmentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

import static com.egles121.mngmtsys.constants.Constants.DEPARTMENT_EMPTY;
import static com.egles121.mngmtsys.constants.Constants.DEPARTMENT_NOT_FOUND;

@Component
public class EmployeeMapper {

    @Autowired
    private DepartmentRepository departmentRepository;

    public EmployeeDto mapEmployeeDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(employee.getId());
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setJobTitle(employee.getJobTitle());
        employeeDto.setDepartmentName(employee.getDepartment().getName());
        employeeDto.setStartDate(employee.getStartDate());
        if (employee.getEndDate() != null) {
            employeeDto.setTerminated(true);
            employeeDto.setEndDate(employee.getEndDate());
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
        if (employeeDto.getId() != null) {
            employee.setId(employeeDto.getId());
        }
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setJobTitle(employeeDto.getJobTitle());
        employee.setDepartment(departmentRepository.findByName(employeeDto.getDepartmentName()));
        employee.setStartDate(employeeDto.getStartDate() == null ? Instant.now() : employeeDto.getStartDate());
        if (employeeDto.isTerminated() && employee.getEndDate() == null) {
            employee.setEndDate(Instant.now());
        }
        return employee;
    }
}
