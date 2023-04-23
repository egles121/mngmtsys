package com.egles121.mngmtsys.services;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.dto.mapping.EmployeeMapper;
import com.egles121.mngmtsys.exception.ManagementAppException;
import com.egles121.mngmtsys.model.Employee;
import com.egles121.mngmtsys.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.egles121.mngmtsys.constants.Constants.EMPLOYEE_NOT_FOUND;

@RequiredArgsConstructor
@Service
@Slf4j
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    public List<EmployeeDto> findAllEmployees() {
        return employeeRepository.findAll().stream().map(employeeMapper::mapEmployeeDto).collect(Collectors.toList());
    }

    public EmployeeDto findEmployeeById(Long id) {
        log.info("Checking if employee with id: {} exists", id);
        return employeeRepository.findEmployeeById(id)
                .flatMap(employee -> Optional.of(employeeMapper.mapEmployeeDto(employee)))
                .orElseThrow(() -> new ManagementAppException(EMPLOYEE_NOT_FOUND));
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee newEmployee = employeeRepository.save(employeeMapper.mapEmployee(employeeDto));
        return employeeMapper.mapEmployeeDto(newEmployee);
    }

    public EmployeeDto updateEmployeeDetails(Long id, EmployeeDto employeeDto) {
        Employee newEmployee = employeeRepository.save(employeeMapper.mapEmployee(employeeDto));
        log.info("Employee with provided id:{} has been updated", id);
        return employeeMapper.mapEmployeeDto(newEmployee);
    }

    @Transactional
    public void deleteEmployee(Long id) {
        employeeRepository.deleteEmployeeById(id);
    }
}
