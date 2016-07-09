package com.cne.rest.cxf.service;

import static org.junit.Assert.*;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.client.WebClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cne.rest.cxf.common.EndPoint;

/**
 * 
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:*/WEB-INF/web.xml"})
public class EmbeddedJetty {

    private static Server server;
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        try {
            
            System.setProperty("spring.profiles.active", "development");
            
            server = new Server(8081);
            WebAppContext context = new WebAppContext();
            context.setDescriptor("src/main/webapp/WEB-INF/web.xml");
            context.setResourceBase("src/main/webapp");
            context.setContextPath("/com.cne.rest.cxf");
            context.setParentLoaderPriority(true);
            
            server.setHandler(context);
            server.start();
            
            System.out.println("Server started at http://localhost:"+8081+"/com.cne.rest.cxf");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        server.stop();
        server.destroy();
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
        
    }

    @Test
    public void testGet() {
        assertNotNull(server);
        
        StringBuilder result=new StringBuilder();
        
        try {
            
            WebClient webclient = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress() + "/endclientsearch/execute/privates?lang=en&mandat=V0001");
            webclient.header("Authorization", "Basic YWRtaW46YWRtaW4xMjM=");
            webclient.accept(MediaType.APPLICATION_JSON);
            webclient.type(MediaType.APPLICATION_JSON);
            
            //Response response = client.post(new String("{ \"name\" : \"Carlos N. Esplana\"}"));
            Response response = webclient.get();
            assertNotNull(response);
            
            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }
    
    @Test
    public void testPost() {
        assertNotNull(server);
        
        try {
            
            WebClient webclient = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress() + "/endclientsearch/execute/privates?lang=en&mandat=V0001");
            webclient.header("Authorization", "Basic YWRtaW46YWRtaW4xMjM=");
            webclient.accept(MediaType.APPLICATION_JSON);
            webclient.type(MediaType.APPLICATION_JSON);
            
            Response response = webclient.post(new String("{ \"name\" : \"Carlos N. Esplana\"}"));
            assertNotNull(response);
            
            //assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));
            
        } catch (Exception e) {
            fail(e.getMessage());
        }
        
    }
    
    /**
     * 
     * @param response
     * @return
     */
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

}
