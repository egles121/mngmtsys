package com.egles121.mngmtsys.controllers;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TestControllerTest {

    private MockMvc mockMvc;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private TestController testController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
    }

    @Test
    void test_getAllEmployees() throws Exception {
        //given
        when(employeeService.findAllEmployees()).thenReturn(List.of(new EmployeeDto()));
        //when-then
        mockMvc.perform(get("/api/v1/test/find/all"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/test/find"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).findAllEmployees();
    }

    @Test
    void test_getEmployeeById() throws Exception {
        //given
        when(employeeService.findEmployeeById(anyLong())).thenReturn(new EmployeeDto());
        //when-then
        mockMvc.perform(get("/api/v1/test/1/find"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/test/abc/find"))
                .andExpect(status().is4xxClientError());

        verify(employeeService, times(1)).findEmployeeById(anyLong());
    }

    @Test
    void test_addNewEmployee() throws Exception {
        //given
        EmployeeDto employeeDto = createEmployeeDto();
        when(employeeService.createEmployee(employeeDto)).thenReturn(employeeDto);
        //when-then
        mockMvc.perform(post("/api/v1/test/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/v1/test/addd"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).createEmployee(employeeDto);
    }

    @Test
    void test_updateExistingEmployee() throws Exception {
        //when-then
        EmployeeDto employeeDto = createEmployeeDto();
        mockMvc.perform(put("/api/v1/test/1/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(employeeDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        mockMvc.perform(put("/api/v1/test/abc/update"))
                .andExpect(status().is4xxClientError());

        verify(employeeService, times(1)).updateEmployeeDetails(1L, employeeDto);
    }

    @Test
    void test_deleteEmployee() throws Exception {
        //when-then
        mockMvc.perform(delete("/api/v1/test/1/delete"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/test/del"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).deleteEmployee(anyLong());
    }

    private EmployeeDto createEmployeeDto() {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setFirstName("test-firstName");
        employeeDto.setLastName("test-lastName");
        employeeDto.setJobTitle("test-jobTitle");
        employeeDto.setDepartmentName("test-department");
        return employeeDto;
    }
}