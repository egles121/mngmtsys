package com.egles121.mngmtsys.services;

import com.egles121.mngmtsys.constants.Constants;
import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.dto.mapping.EmployeeMapper;
import com.egles121.mngmtsys.exception.ManagementAppException;
import com.egles121.mngmtsys.model.Department;
import com.egles121.mngmtsys.model.Employee;
import com.egles121.mngmtsys.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void test_findAllEmployees() {
        //given
        Employee employee = createEmployee();
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.mapEmployeeDto(employee)).thenReturn(new EmployeeDto());
        //when
        List<EmployeeDto> result = employeeService.findAllEmployees();
        //then
        assertEquals(1, result.size());
    }

    @Test
    void test_findEmployeeById_successful() {
        //given
        Employee employee = createEmployee();
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(Optional.of(employee));
        when(employeeMapper.mapEmployeeDto(employee)).thenReturn(new EmployeeDto());
        //when
        EmployeeDto employeeDto = employeeService.findEmployeeById(anyLong());
        //then
        verify(employeeRepository, times(1)).findEmployeeById(anyLong());
        verify(employeeMapper, times(1)).mapEmployeeDto(employee);
    }

    @Test
    void test_findEmployeeById_throwsException() {
        //given
        Employee employee = createEmployee();
        when(employeeRepository.findEmployeeById(anyLong())).thenReturn(Optional.empty());
        //when
        ManagementAppException managementAppException = assertThrows(ManagementAppException.class,
                () -> employeeService.findEmployeeById(10L));
        //then
        assertEquals(Constants.EMPLOYEE_NOT_FOUND, managementAppException.getMessage());
        verify(employeeRepository, times(1)).findEmployeeById(anyLong());
        verify(employeeMapper, never()).mapEmployeeDto(employee);
    }

    @Test
    void test_createEmployee() {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        Employee employee = createEmployee();
        when(employeeMapper.mapEmployee(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapEmployeeDto(employee)).thenReturn(employeeDto);
        //when
        employeeService.createEmployee(employeeDto);
        //then
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).mapEmployeeDto(employee);
        verify(employeeMapper, times(1)).mapEmployee(employeeDto);
    }

    @Test
    void test_updateEmployee() {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        Employee employee = createEmployee();
        when(employeeMapper.mapEmployee(employeeDto)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.mapEmployeeDto(employee)).thenReturn(employeeDto);
        //when
        employeeService.updateEmployeeDetails(1L, employeeDto);
        //then
        verify(employeeRepository, times(1)).save(employee);
        verify(employeeMapper, times(1)).mapEmployeeDto(employee);
        verify(employeeMapper, times(1)).mapEmployee(employeeDto);
    }

    @Test
    void test_deleteEmployee() {
        //when
        employeeService.deleteEmployee(anyLong());
        //then
        verify(employeeRepository, times(1)).deleteEmployeeById(anyLong());
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("test-firstName");
        employee.setLastName("test-lastName");
        employee.setJobTitle("test-jobTitle");
        employee.setDepartment(new Department());
        employee.setStartDate(Instant.now());
        return employee;
    }

    private Department createDepartment() {
        Department department = new Department();
        department.setId(2L);
        department.setName("test-departmentName");
        return department;
    }

    private EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("test-firstName");
        employeeDto.setLastName("test-lastName");
        employeeDto.setJobTitle("test-jobTitle");
        employeeDto.setDepartmentName("test-department");
        employeeDto.setStartDate(Instant.now());
        return employeeDto;
    }
}