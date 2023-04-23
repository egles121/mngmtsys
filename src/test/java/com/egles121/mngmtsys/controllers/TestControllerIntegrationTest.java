package com.egles121.mngmtsys.controllers;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.dto.mapping.EmployeeMapper;
import com.egles121.mngmtsys.repositories.DepartmentRepository;
import com.egles121.mngmtsys.repositories.EmployeeRepository;
import com.egles121.mngmtsys.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TestControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeMapper employeeMapper;

    @BeforeEach
    @Transactional
    void setUp() {
        employeeRepository.deleteAll();
    }

    @Transactional
    @Test
    void test_getAllEmployees() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        employeeRepository.save(employeeMapper.mapEmployee(employeeDto));
        employeeRepository.save(employeeMapper.mapEmployee(employeeDto));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/test/find/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Transactional
    @Test
    void test_findEmployeeById() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        EmployeeDto savedEmployee = employeeMapper.mapEmployeeDto(employeeRepository.save(employeeMapper.mapEmployee(employeeDto)));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/test/" + savedEmployee.getId() + "/find/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        //then
        response.andDo(print()).
                andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName",
                        is(employeeDto.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employeeDto.getLastName())))
                .andExpect(jsonPath("$.departmentName",
                        is(employeeDto.getDepartmentName())))
                .andExpect(jsonPath("$.jobTitle",
                        is(employeeDto.getJobTitle())));
    }


    @Transactional
    @Test
    void test_addNewEmployee_givenEmployeeDto_returnEmployee() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/test/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().findAndRegisterModules().writeValueAsString(employeeDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(employeeDto.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(employeeDto.getLastName())))
                .andExpect(jsonPath("$.departmentName",
                        is(employeeDto.getDepartmentName())))
                .andExpect(jsonPath("$.jobTitle",
                        is(employeeDto.getJobTitle())));
    }

    @Transactional
    @Test
    void test_updateExistingEmployee() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        EmployeeDto savedEmployee = employeeMapper.mapEmployeeDto(employeeRepository.save(employeeMapper.mapEmployee(employeeDto)));
        EmployeeDto requestBody = new EmployeeDto();
        requestBody.setFirstName("Bella");
        requestBody.setLastName("Goth");
        requestBody.setJobTitle("HR Representative");
        requestBody.setDepartmentName("HR");

        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/test/" + savedEmployee.getId() + "/update/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().findAndRegisterModules().writeValueAsString(requestBody))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        //then
        response.andDo(print()).
                andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName",
                        is(requestBody.getFirstName())))
                .andExpect(jsonPath("$.lastName",
                        is(requestBody.getLastName())))
                .andExpect(jsonPath("$.departmentName",
                        is(requestBody.getDepartmentName())))
                .andExpect(jsonPath("$.jobTitle",
                        is(requestBody.getJobTitle())));
    }

    @Transactional
    @Test
    void test_deleteEmployee() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        EmployeeDto savedEmployee = employeeMapper.mapEmployeeDto(employeeRepository.save(employeeMapper.mapEmployee(employeeDto)));
        //when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/test/" + savedEmployee.getId() + "/delete/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        //then
        response.andDo(print()).
                andExpect(status().is2xxSuccessful());
    }

    private EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("test-firstName");
        employeeDto.setLastName("test-lastName");
        employeeDto.setJobTitle("test-jobTitle");
        employeeDto.setDepartmentName("OPERATIONS");
        return employeeDto;
    }
}