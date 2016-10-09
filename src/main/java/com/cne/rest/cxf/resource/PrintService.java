package com.cne.rest.cxf.resource;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.codec.binary.Base64;

import com.cne.rest.cxf.model.Employee;
import com.cne.rest.cxf.model.ReportDocumentModel;
import com.cne.rest.report.builder.OutputBuilder;

@Path("/printservice")
public class PrintService {
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public Response doPrint() {
		ReportDocumentModel model = new ReportDocumentModel();
		model.setCompanyname("ABC Inc.");
		List<Employee> employees = new ArrayList<>();
		
		Employee employee = new Employee();
		employee.setId("101");
		employee.setFirstname("James");
		employee.setLastname("Gosling");
		employee.setDesignation("Executive");
		employees.add(employee);
		
		employee = new Employee();
		employee.setId("102");
		employee.setFirstname("Bill");
		employee.setLastname("Gates");
		employee.setDesignation("Executive");
		employees.add(employee);
		model.setEmployees(employees);
		
		byte[] generatedPDF = OutputBuilder.buildReport(model);
	    
		ResponseBuilder responseBuilder = Response.ok(generatedPDF);
		responseBuilder.type("application/pdf");
		responseBuilder.header("Content-Disposition", "attachment; filename=print-overview.pdf");
		 
		return responseBuilder.build();
	}
}
