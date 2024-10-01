package com.company.controller;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;


import com.company.entity.ConfirmationForm;
import com.company.entity.EmployeeEntity;
import com.company.repository.EmployeeRepository;

import jakarta.validation.Valid;


@Controller

public class EmployeeController {



	@Autowired
     private EmployeeRepository employeeRepo;
	 // Calculate age based on DOB (Date of Birth)
    private int calculateAge(LocalDate dob) {
        if (dob != null) {
            return Period.between(dob, LocalDate.now()).getYears();
        }
        return 0; // Default age if dob is null
    }

     // display the html page
     @GetMapping("/")
     public String getIndex(Model model) {
         List<EmployeeEntity> employeeList = employeeRepo.findAll();
         
         // Dynamically calculate and set age for each employee
         for (EmployeeEntity employee : employeeList) {
             employee.setAge(calculateAge(employee.getDob()));
         }
         model.addAttribute("employees", employeeList);
         model.addAttribute("employee", new EmployeeEntity());
         model.addAttribute("confirmationForm", new ConfirmationForm());
         return "index";
     }
     // Insert employee data
     @PostMapping("/create")
     public String newEmployee(@Valid  @ModelAttribute EmployeeEntity employee, BindingResult result, Model model) {
         if (result.hasErrors()) {
             model.addAttribute("employees", employeeRepo.findAll());
             return "index"; // Return the form with errors
         }
         employeeRepo.save(employee);
         return "redirect:/";
     }
     // update the existing employee
    @PostMapping("/update")
    public String updateEmployee(@Valid @ModelAttribute EmployeeEntity employee, BindingResult result, Model model) {
        model.addAttribute("employee", new EmployeeEntity());
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeRepo.findAll());
            return "index"; // Return the form with errors
        }
         Optional<EmployeeEntity> existingEmployee = employeeRepo.findById(employee.getId());

         // checking employee exist or not
         if (existingEmployee.isPresent()) {
             employeeRepo.save(employee);  // Save the updated employee entity
         } else {
             model.addAttribute("errorMessage", "Employee with ID " + employee.getId() + " not found.");
         }
         return "redirect:/";
     }
   
       

     // delete all employees data by confirmation
     @PostMapping("/remove/all")
     public String removeAll(@ModelAttribute ConfirmationForm confirmationForm, Model model) {
         String confirmation = confirmationForm.getConfirmation();
         if ("Yes".equalsIgnoreCase(confirmation)) {
             employeeRepo.deleteAll();
         } else {
             return "redirect:/";
         }
         return "redirect:/";
     }
     @PostMapping("/remove")
     public String removeEmployee(EmployeeEntity employee, Model model) {
         model.addAttribute("employee", new EmployeeEntity());
         Optional<EmployeeEntity> existingEmployee = employeeRepo.findById(employee.getId());
         if (existingEmployee.isPresent()) {
             employeeRepo.deleteById(employee.getId());
         }
         return "redirect:/";
     }
    
}

