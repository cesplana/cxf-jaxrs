package com.cne.rest.cxf.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Description;

import com.cne.rest.cxf.model.Account;

/**
 * 
 * @author lenovo
 *
 */
@Description("Account Service")
@Path("/accountservice")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountService {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Map<String, Account> accountMap = new HashMap<>();

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
    
    /**
     * Default Constructor
     */
    public AccountService() {
        Account account = new Account();
        account.setId(23);
        account.setName("Carlos N. Esplana");
        accountMap.put("1", account);

        account = new Account();
        account.setId(235);
        account.setName("Esplana");
        accountMap.put("2", account);
    }

    @GET
    @Path("/account/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("id") String id) {
        
        logger.info("AccountService.getAccount()>>>>>>>>>>>> id:={}", id);
        
        Account c = accountMap.get(id);

        if (c == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        return Response.ok(c).build();
    }

    @GET
    @Path("/accounts/getall")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccounts() {

        logger.info("AccountService.getAllAccounts()>>>>>>>>>>>>");
        
        final List<Account> accountList = new ArrayList<>();

        for (Map.Entry<String, Account> entry: accountMap.entrySet()) {
            accountList.add(accountMap.get(entry.getKey()));
        }

        return Response.ok(accountList).build();
    }
}
