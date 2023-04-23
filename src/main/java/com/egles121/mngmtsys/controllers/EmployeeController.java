package com.egles121.mngmtsys.controllers;

import com.egles121.mngmtsys.dto.EmployeeDto;
import com.egles121.mngmtsys.services.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("api/v1/employee")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ModelAndView getAllEmployees() {
        ModelAndView modelAndView = new ModelAndView("list-employees");
        Object object = employeeService.findAllEmployees();
        modelAndView.addObject("employees", object);
        return modelAndView;
    }

    @GetMapping("/add/form")
    public ModelAndView addEmployeeForm() {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        EmployeeDto employeeDto = new EmployeeDto();
        boolean isAddForm = true;
        modelAndView.addObject("employee", employeeDto);
        modelAndView.addObject("isAddForm", isAddForm);
        return modelAndView;
    }

    @PostMapping("/post")
    public String saveEmployee(@ModelAttribute EmployeeDto employeeDto) {
        employeeService.createEmployee(employeeDto);
        return "redirect:/api/v1/employee/all";
    }

    @GetMapping("/update/form")
    public ModelAndView showUpdateForm(@RequestParam Long employeeId) {
        ModelAndView modelAndView = new ModelAndView("add-employee-form");
        EmployeeDto employeeDto = employeeService.findEmployeeById(employeeId);
        modelAndView.addObject("employee", employeeDto);
        return modelAndView;
    }

    @GetMapping("/delete")
    public String deleteEmployee(@RequestParam Long employeeId) {
        employeeService.deleteEmployee(employeeId);
        return "redirect:/api/v1/employee/all";
    }
}
