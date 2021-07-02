package com.example.jasper.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.jasper.model.Employee;
import com.example.jasper.service.EmployeeService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Controller
@RequestMapping(value = "/api")
public class EmployeeController {

	final Logger log = LoggerFactory.getLogger(this.getClass());
	final ModelAndView model = new ModelAndView();
	
	@Autowired
	EmployeeService eservice;
	
	@GetMapping(value = "/welcome")
	public ModelAndView index() {
		log.info("Showing the welcome Page");
		model.setViewName("welcome");
		return model;
	}
	
	@GetMapping(value = "/view")
	public ModelAndView viewReport() {
		log.info("Preparing the pdf report via jasper");
		try {
			createPdfReport(eservice.findAll());
			log.info("File Successfully saved at the given path. ");
		} catch (final Exception e) {
			log.error("Some error has ocurred while preparing the employee pdf report.");
			e.printStackTrace();
		}
		model.setViewName("welcome");
		return model;
	}
	
	private void createPdfReport(final List<Employee> employees) throws JRException {
	
		final InputStream stream = this.getClass().getResourceAsStream("/report.jrxml");
		final JasperReport report = JasperCompileManager.compileReport(stream);
		
		final JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(employees);
		
		final Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy","javacodegeek.com");
		
		final JasperPrint print = JasperFillManager.fillReport(report, parameters, source);
		
		final String filePath = "C:\\reports\\";
		
		JasperExportManager.exportReportToPdfFile(print, filePath + "Employee_report.pdf");		
	}
	
}
