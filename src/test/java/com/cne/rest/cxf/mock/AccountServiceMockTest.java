package com.cne.rest.cxf.mock;

import static org.junit.Assert.fail;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.jboss.resteasy.core.Dispatcher;
import org.jboss.resteasy.mock.MockDispatcherFactory;
import org.jboss.resteasy.mock.MockHttpRequest;
import org.jboss.resteasy.mock.MockHttpResponse;
import org.jboss.resteasy.plugins.server.embedded.SecurityDomain;
import org.jboss.resteasy.plugins.server.embedded.SimpleSecurityDomain;
import org.jboss.resteasy.plugins.server.resourcefactory.POJOResourceFactory;
import org.jboss.resteasy.plugins.server.tjws.TJWSEmbeddedJaxrsServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.cne.rest.cxf.common.EndPoint;
import com.cne.rest.cxf.resource.EndClientSearchResource;
import com.cne.rest.cxf.service.AccountService;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;

/**
 * 
 * @author lenovo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-security.xml", "classpath:*/WEB-INF/web.xml" })
@WebAppConfiguration
public class AccountServiceMockTest {
   
  
    private static TJWSEmbeddedJaxrsServer server;
    
    static class BasicAuthenticationSecurityDomain implements SecurityDomain {

        @Override
        public Principal authenticate(String username, String password) throws SecurityException {
            System.out.println("User:" + username + " Password" + password);

            if (password.equals("admin123") == false) {
                throw new SecurityException("Access denied to user " + username);
            }

            return null;
        }

        @Override
        public boolean isUserInRole(Principal username, String role) {
            return true;
        }
        
    }
    
    public AccountServiceMockTest() {
        // TODO Auto-generated constructor stub
    }
    
    @BeforeClass
    public static void beforeClass() {
        server = new TJWSEmbeddedJaxrsServer();
        server.setPort(8081);
        server.setRootResourcePath("/com.cne.rest.cxf/presentation");
        server.getDeployment().setSecurityEnabled(true);
        server.setSecurityDomain(new BasicAuthenticationSecurityDomain());
        
        /*final SimpleSecurityDomain securityDomain = new SimpleSecurityDomain();
        securityDomain.addUser("super", "pass", new String[] { "admin" });
        server.setSecurityDomain(securityDomain);*/

        server.start();
        server.getDeployment().getDispatcher().getRegistry().addSingletonResource(new EndClientSearchResource());
        //server.getDeployment().getDispatcher().getRegistry().addSingletonResource(new AccountService());
        
        
    }
    
    @Test
    @Ignore
    public void testAccountService() {
        try {
            
            //assertNotNull(server);
            
            POJOResourceFactory noDefaults = new POJOResourceFactory(EndClientSearchResource.class);
            Dispatcher dispatcher = MockDispatcherFactory.createDispatcher();
            dispatcher.getRegistry().addResourceFactory(noDefaults);
            MockHttpRequest request = MockHttpRequest.get("http://localhost:8081/com.cne.rest.cxf/presentation/accountservice/accounts/getall");
            request.accept(MediaType.APPLICATION_JSON);
            request.contentType(MediaType.APPLICATION_JSON_TYPE);
            request.header("Authorization", "Basic username:password");
            
            // res being your resource object
            //request.content(res.toJSONString().getBytes());

            MockHttpResponse response = new MockHttpResponse();
            dispatcher.invoke(request, response);
            
            System.out.printf("%d", HttpStatus.SC_CREATED,response.getStatus());
            System.out.println("Response Payload: " + response.getContentAsString());
            Client client = ClientBuilder.newClient();
            
            /*Response response = client.target("http://localhost:8081/com.cne.rest.cxf/presentation/accountservice/accounts/getall")
                    .request().header("Content-Type", MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).get();
            
            System.out.println("\nResponse Payload>>> " + response.readEntity(String.class));
            System.out.println();
            
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());*/
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @Test
    public void testEndClientSearch() {
        try {
            assertNotNull(server);
            
            Client client = ClientBuilder.newClient().register(new Authenticator("admin", "admin123"));
            
            String requestPayload = "{ \"name\" : \"Carlos N. Esplana\"}";
            
            Response response = client.target("http://localhost:8081/com.cne.rest.cxf/presentation/endclientsearch/execute")
                    .request().header("Content-Type", MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON).post(Entity.entity(requestPayload, MediaType.APPLICATION_JSON));
            
            System.out.println("\nResponse Payload>>> " + response.readEntity(String.class));
            System.out.println();
            
            assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
            
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
    
    @AfterClass
    public static void afterClass() {
        server.stop();
    }
    
    class Authenticator implements ClientRequestFilter {

        private final String user;
        private final String password;

        public Authenticator(String user, String password) {
            this.user = user;
            this.password = password;
        }

        public void filter(ClientRequestContext requestContext) throws IOException {
            MultivaluedMap<String, Object> headers = requestContext.getHeaders();
            final String basicAuthentication = getBasicAuthentication();
            headers.add("Authorization", basicAuthentication);

        }

        private String getBasicAuthentication() {
            String token = this.user + ":" + this.password;
            try {
                return "Basic " + DatatypeConverter.printBase64Binary(token.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                throw new IllegalStateException("Cannot encode with UTF-8", ex);
            }
        }
    }
    
}
