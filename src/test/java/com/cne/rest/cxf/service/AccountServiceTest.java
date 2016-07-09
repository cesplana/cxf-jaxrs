package com.cne.rest.cxf.service;

import static org.junit.Assert.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cne.rest.cxf.common.EndPoint;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:*/WEB-INF/web.xml"})
public class AccountServiceTest { //NOSONAR

    private static Server server;

    public AccountServiceTest() {
        // Default Contructor
    }

    @BeforeClass
    public static void initialize() throws Exception {

        try {
            startServer();
            generateWADL();
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    private static void startServer() throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(AccountService.class);

        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();
   
        sf.setProvider(jsonProvider);
        sf.setResourceProvider(AccountService.class, new SingletonResourceProvider(new AccountService(), true));
        sf.setAddress(EndPoint.ENDPOINT_ADDRESS.getAddress());
        server = sf.create();
    }

    private static void generateWADL() throws Exception {

        System.out.println("Generating WADL...");

        WebClient client = WebClient
                .create(EndPoint.ENDPOINT_ADDRESS.getAddress() + EndPoint.WADL_ADDRESS.getAddress());

        for (int i = 1; i <= 20; i++) {
            Thread.sleep(1000);
            Response response = client.get();

            if (response.getStatus() == 200) {
                System.out.println("WADL successfully generated!");
                break;
            }
        }
    }

    @Test
    @Ignore
    public void testAccountService() {
        try {

            assertNotNull(server);
            
            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);

            client.path("/accountservice/account/2");

            Response response = client.get();
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testGetAccountsService() {
        try {

            assertNotNull(server);
            
            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);

            client.path("/accountservice/accounts/getall");

            Response response = client.get();
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    @Ignore
    public void testBadRequest() {
        try {

            assertNotNull(server);
            
            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);

            client.path("/accountservice/account/5");

            Response response = client.get();
            assertNotNull(response);

            assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    private int displayResponse(Response response) {
        String responsePayload = response.readEntity(String.class);
        System.out.println("----------- RESPONSE HEADERS --------- ");
        System.out.println("Metadata: " + response.getMetadata());
        System.out.println("Status: " + response.getStatus());
        System.out.println("--------------------------------------");
        System.out.println("----------- RESPONSE PAYLOAD --------- ");
        System.out.println(responsePayload);
        System.out.println("--------------------------------------");
        
        return response.getStatus();
    }
    
    @AfterClass
    public static void distroy() {
        try {

            assertNotNull(server);
            server.stop();
            server.destroy();

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
