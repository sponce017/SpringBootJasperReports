package com.example.jasper.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.jasper.model.Employee;
import com.github.javafaker.Faker;


@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	final Faker faker = new Faker();
	final Random random = new Random();

	@Override
	public List<Employee> findAll() {
		final List<Employee> employees = new ArrayList<>();
		
		for (int count = 1; count <21; count++) {
			employees.add(new Employee(random.nextInt(30+1), faker.name().fullName(),
					faker.job().title(), faker.job().field()));
		}		
		return employees;
	}

}
