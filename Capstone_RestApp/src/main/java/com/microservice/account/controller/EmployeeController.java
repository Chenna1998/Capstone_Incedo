package com.microservice.account.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.account.dto.ResponseDto;
import com.microservice.account.enums.RoleType;
import com.microservice.account.exception.ResourceNotFoundException;
import com.microservice.account.model.Employee;
import com.microservice.account.model.Manager;
import com.microservice.account.model.UserInfo;
import com.microservice.account.service.EmployeeService;
import com.microservice.account.service.ManagerService;
import com.microservice.account.service.UserInfoService;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private UserInfoService userInfoService;
	
	@Autowired
	private ManagerService managerService;
	
	@PostMapping("/api/employee/add/{managerId}") //authenticate
	public ResponseEntity<?> postEmployee(@PathVariable("managerId") int managerId,
									@RequestBody Employee employee) {
		
		Manager managerObj = null; 
		try {
			managerObj = managerService.getManagerById(managerId);
		} catch (ResourceNotFoundException e) {
			 return ResponseEntity.badRequest()
					 .body(new ResponseDto(e.getMessage(), "400"));
		}
		employee.setManager(managerObj);
		UserInfo userInfo = employee.getUserInfo();
		userInfo.setRole(RoleType.EMPLOYEE);
		userInfo = userInfoService.insertUserInfo(userInfo);
		employee.setUserInfo(userInfo);
		employee = employeeService.addEmployee(employee);
		return ResponseEntity.ok().body(employee) ;
	}
	
	@GetMapping("/api/employee/getall") 
	public List<Employee> getAllEmployee() {
		List<Employee> list = employeeService.getAllEmployee();
		return list; 
	}
	
	@DeleteMapping("/api/employee/delete/{id}")
	public String deleteEmployee(@PathVariable("id") int id) {
		employeeService.deleteEmployee(id);
		return "Employee deleted..";
	}
	@PutMapping("/api/employee/edit/{id}")
	public ResponseEntity<?> editEmployee(@PathVariable("id") int id, 
								@RequestBody Employee employeeNew) {
		try {
			Employee employeeOld = employeeService.getEmployeeById(id);
			/*update fields */
			employeeOld.setCity(employeeNew.getCity());
			employeeOld.setSalary(employeeNew.getSalary());
			employeeOld.setJobTitle(employeeNew.getJobTitle());
			/* insert employeeOld */
			employeeOld = employeeService.addEmployee(employeeOld);
			
			return ResponseEntity.ok().body(employeeOld);
		} catch (ResourceNotFoundException e) {
			ResponseDto dto = new ResponseDto(e.getMessage(), "400");
			return ResponseEntity.badRequest().body(dto);
		}
	}
	@GetMapping("/api/jobtype")
	public List<String> getAllJobTitle() {
		List<String> list = employeeService.getAllJobTitle();
		return list;	
	}
	
	@GetMapping("/api/employee/rewardpoints")
	public int getEmployeeRewardPoints(Principal principal) {
		String username = principal.getName();
		return employeeService.getEmployeeRewardPoints(username);		
	}
	
	@PutMapping("/api/update/employee/rewardpoints/{rewardPoints}")
	public ResponseEntity<?> updateEmployeeRewardPointsAfterPurchase(@PathVariable("rewardPoints") int rewardPoints, Principal principal) {
		String username = principal.getName();
		Employee employee = employeeService.getEmployeeByUserName(username);
		employee.setRewardPoints(rewardPoints);
		employee = employeeService.addEmployee(employee);
		return ResponseEntity.ok().body(employee);
		
	}
	
	

}
