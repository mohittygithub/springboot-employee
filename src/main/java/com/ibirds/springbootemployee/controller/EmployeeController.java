package com.ibirds.springbootemployee.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibirds.springbootemployee.exception.ResourceNotFoundException;
import com.ibirds.springbootemployee.model.Employee;
import com.ibirds.springbootemployee.repository.EmployeeRepository;


@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository empRepo;
	
	// get employees
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return this.empRepo.findAll();
	}
	
	// get employee by id
	@GetMapping("/employee/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable(value="id") Long empId) throws ResourceNotFoundException{
		Employee employee = empRepo.findById(empId)
				.orElseThrow(()->new ResourceNotFoundException("Employee not found for this id: " + empId));
		return ResponseEntity.ok().body(employee);
	}
	
	// save employee
	@PostMapping("/employees")
	public Employee createEmployee(@RequestBody Employee employee){
		return this.empRepo.save(employee);
	}
	
	// update employee
	@PutMapping("/employees/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable(value="id") Long empId, @RequestBody Employee empDetails) throws ResourceNotFoundException {
		Employee employee = empRepo.findById(empId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id: " + empId));
		employee.setEmail(empDetails.getEmail());
		employee.setFirstName(empDetails.getFirstName());
		employee.setLastName(empDetails.getLastName());
		
		return ResponseEntity.ok(this.empRepo.save(employee));
	}
	// delete employee
	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value="id") Long empId) throws ResourceNotFoundException{
		Employee employee = empRepo.findById(empId)
				.orElseThrow(()-> new ResourceNotFoundException("Employee not found for this id: " + empId));
		
		this.empRepo.delete(employee);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		
		return response;
		
		
		
	}
}
