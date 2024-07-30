package com.example.lab6.Controller;

import com.example.lab6.Model.Employees;
import com.example.lab6.Response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employees>employees=new ArrayList<>();
    @GetMapping("/get")
    public ResponseEntity getEmployee(){
        return ResponseEntity.status(200).body(employees);
    }
    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employees employee, Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiResponse("Success add"));
    }
    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@PathVariable int index,@Valid@RequestBody Employees employee,Errors errors){
        if(errors.hasErrors()){
            String message=errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(message);
        }
        employees.set(index,employee);
        return ResponseEntity.status(200).body(new ApiResponse("Success update"));
    }
    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index){
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiResponse("Success delete"));

    }
    @GetMapping("/search/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position){
        ArrayList<Employees>employeesPosition=new ArrayList<>();
        for(Employees e:employees){
            if(e.getPosition().equalsIgnoreCase(position)){
                employeesPosition.add(e);
            }
        }
        return ResponseEntity.status(200).body(employeesPosition);
    }
  @GetMapping("/searcha/{minAge}/{maxAge}")
    public ResponseEntity searchAgeRange(@PathVariable int  minAge,@PathVariable int maxAge ){
      ArrayList<Employees>employeesArrayList=new ArrayList<>();
       for(Employees e:employees){
           if(e.getAge()>=minAge&&e.getAge()<=maxAge){
               employeesArrayList.add(e);
           }
       }
       return ResponseEntity.status(200).body(employeesArrayList);

    }
    @PutMapping("/applay/{id}")
    public ResponseEntity ApplyAnnualLeave(@PathVariable String id){
        for (Employees empl:employees){
            if(empl.getId().equals(id)&&empl.getOnLeave().equals(false)&&empl.getAnnualLeave()>=1){
                        empl.setOnLeave(true);
                        empl.setAnnualLeave(empl.getAnnualLeave()-1);
                        return ResponseEntity.status(200).body(empl);
            }
        }
        return ResponseEntity.status(400).body(new ApiResponse("can not apply  annual leave"));
    }
    @GetMapping("/annual")
    public ResponseEntity getEmployeesNoAnnualLeave(){
         ArrayList<Employees>employeesNoAnnual=new ArrayList<>();
         for(Employees e:employees){
             if(e.getAnnualLeave()==0){
                 employeesNoAnnual.add(e);
             }
        }
         if(employeesNoAnnual.isEmpty())
             return ResponseEntity.status(400).body(new ApiResponse("There is no one who does not have annual leave."));
         return ResponseEntity.status(200).body(employeesNoAnnual);
    }
    @PutMapping("/promote/{supervisorId}/{employeeId}")
    public ResponseEntity PromoteEmployee(@PathVariable String supervisorId,@PathVariable String employeeId){
      for(Employees e:employees) {
          if(e.getId().equals(supervisorId)&&e.getPosition().equalsIgnoreCase("supervisor")){
              for (Employees em:employees){
                  if(em.getId().equals(employeeId)&&em.getPosition().equalsIgnoreCase("coordinator")&&em.getAge()>=30&&em.getOnLeave().equals(false)){
                      em.setPosition("supervisor");
                      return ResponseEntity.status(200).body(em);
                  }
              }
          }
      }
      return ResponseEntity.status(400).body(new ApiResponse("Not found,Age must be 30 years or older,There should be no OnLeave."));

    }










}
