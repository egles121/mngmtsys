package com.egles121.mngmtsys.controllers;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.services.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {
    private MockMvc mockMvc;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    void test_getAllEmployees() throws Exception {
        //given
        when(employeeService.findAllEmployees()).thenReturn(List.of(new EmployeeDto()));
        //when-then
        mockMvc.perform(get("/api/v1/employee/all"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/employee/find"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).findAllEmployees();
    }

    @Test
    void test_addEmployeeForm() throws Exception {
        //when-then
        mockMvc.perform(get("/api/v1/employee/add/form"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/employee/add"))
                .andExpect(status().isNotFound());
    }

    @Test
    void test_saveEmployee() throws Exception {
        //when-then
        mockMvc.perform(post("/api/v1/employee/post"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(post("/api/v1/employee/postt"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).createEmployee(new EmployeeDto());
    }

    @Test
    void test_showUpdateForm() throws Exception {
        //when-then
        mockMvc.perform(get("/api/v1/employee/update/form?employeeId=1"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/employee/update"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).findEmployeeById(anyLong());
    }

    @Test
    void test_deleteEmployee() throws Exception {
        //when-then
        mockMvc.perform(get("/api/v1/employee/delete?employeeId=1"))
                .andExpect(status().is3xxRedirection());

        mockMvc.perform(get("/api/v1/employee/del"))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).deleteEmployee(anyLong());
    }
}