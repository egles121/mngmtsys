package com.egles121.mngmtsys.dto.mapping;

import com.egles121.mngmtsys.constants.Constants;
import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.exception.ManagementAppException;
import com.egles121.mngmtsys.model.Department;
import com.egles121.mngmtsys.model.Employee;
import com.egles121.mngmtsys.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeMapperTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @InjectMocks
    private EmployeeMapper employeeMapper;

    @Test
    void test_mapEmployeeDto() {
        //given
        Employee employee = createEmployee();
        employee.setEndDate(Instant.now());
        //when
        EmployeeDto employeeDto = employeeMapper.mapEmployeeDto(employee);
        //then
        assertEquals(employee.getId(), employeeDto.getId());
        assertEquals(employee.getFirstName(), employeeDto.getFirstName());
        assertEquals(employee.getLastName(), employeeDto.getLastName());
        assertEquals(employee.getJobTitle(), employeeDto.getJobTitle());
        assertEquals(employee.getDepartment().getName(), employeeDto.getDepartmentName());
        assertEquals(employee.getStartDate(), employeeDto.getStartDate());
        assertEquals(employee.getEndDate(), employeeDto.getEndDate());
    }

    @Test
    void test_mapEmployee() {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        employeeDto.setTerminated(true);
        Department department = createDepartment();
        when(departmentRepository.findByName(employeeDto.getDepartmentName())).thenReturn(department);
        //when
        Employee employee = employeeMapper.mapEmployee(employeeDto);
        //then
        assertEquals(employeeDto.getId(), employee.getId());
        assertEquals(employeeDto.getFirstName(), employee.getFirstName());
        assertEquals(employeeDto.getLastName(), employee.getLastName());
        assertEquals(employeeDto.getJobTitle(), employee.getJobTitle());
        assertEquals(employeeDto.getDepartmentName(), employee.getDepartment().getName());
        assertEquals(employeeDto.getStartDate(), employee.getStartDate());
        assertNotNull(employee.getEndDate());
    }

    @Test
    void test_mapEmployee_employeeDtoDepartmentNameEmpty() {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        employeeDto.setDepartmentName(null);
        //when
        ManagementAppException managementAppException = assertThrows(ManagementAppException.class,
                () -> employeeMapper.mapEmployee(employeeDto));
        //then
        assertEquals(Constants.DEPARTMENT_EMPTY, managementAppException.getMessage());
    }

    @Test
    void test_mapEmployee_employeeDtoDepartmentNotFound() {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        when(departmentRepository.findByName(employeeDto.getDepartmentName())).thenReturn(null);
        //when
        ManagementAppException managementAppException = assertThrows(ManagementAppException.class,
                () -> employeeMapper.mapEmployee(employeeDto));
        //then
        assertEquals(Constants.DEPARTMENT_NOT_FOUND, managementAppException.getMessage());
    }

    private Employee createEmployee() {
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("test-firstName");
        employee.setLastName("test-lastName");
        employee.setJobTitle("test-jobTitle");
        employee.setDepartment(createDepartment());
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
        employeeDto.setDepartmentName("test-departmentName");
        employeeDto.setStartDate(Instant.now());
        return employeeDto;
    }
}