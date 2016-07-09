package com.cne.rest.cxf.service;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import static org.junit.Assert.*;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.cne.rest.cxf.common.EndPoint;
import com.cne.rest.cxf.model.UserBean;
import com.cne.rest.cxf.resource.EndClientSearchResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableWebSecurity
@ActiveProfiles(profiles = "development")
@ContextConfiguration(locations = {"classpath:*/WEB-INF/web.xml" })
public class EndClientSearchResourceTest {

    private static Server server;

    public EndClientSearchResourceTest() {
        // Default Constructor
    }

    @BeforeClass
    public static void beforeClass() {
        try {
            startServer();
            generateWADL();
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * 
     * @throws Exception
     */
    private static void startServer() throws Exception {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(EndClientSearchResource.class);

        JacksonJsonProvider jsonProvider = new JacksonJsonProvider();

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"classpath:/spring-ws-consumer.xml", "classpath:/spring-security.xml", "classpath:/spring-locale.xml", "classpath:/cxf-rs.xml"});
        EndClientSearchResource endclientSearchResource = context.getBean("endClientSearchResource", EndClientSearchResource.class);
        
        context.close();
        
        sf.setProvider(jsonProvider);
        sf.setResourceProvider(EndClientSearchResource.class, new SingletonResourceProvider(endclientSearchResource, true));
        
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
                String wADL = response.readEntity(String.class);

                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");

                StreamResult result = new StreamResult(new StringWriter());
                DOMSource source = new DOMSource(parseXml(wADL));
                transformer.transform(source, result);

                System.out.println(result.getWriter().toString());
                break;
            }
        }
    }

    /**
     * 
     * @param in
     * @return
     */
    private static Document parseXml(String in) throws Exception {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        InputSource is = new InputSource(new StringReader(in));
        return db.parse(is);

    }

    @Test
    public void testExecuteGET() {
        try {
            assertNotNull(server);

            UserBean userbean = new UserBean();
            userbean.setUsername("admin");
            userbean.setPassword("admin123");
            userbean.setMandator("901");
            
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
            authorities.add(new SimpleGrantedAuthority("ROLE_TEST"));
            
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userbean, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken); 
            
            System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext());
            
            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);
            // client.header("Content-Type", MediaType.APPLICATION_JSON);
            client.path("/endclientsearch/execute/privates?lang=en");
            
            //Response response = client.post(new String("{ \"name\" : \"Carlos N. Esplana\"}"));
            Response response = client.get();
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    @Ignore
    public void testExecute() {
        try {
            assertNotNull(server);

            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);
            // client.header("Content-Type", MediaType.APPLICATION_JSON);
            client.path("/endclientsearch/execute");

            assertNotNull(client);

            Response response = client.post(new String("{ \"name\" : \"Carlos N. Esplana\"}"));
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    @Ignore
    public void testExecuteQueryParam() {
        try {
            assertNotNull(server);
            
            /*List<GrantedAuthority> listOfRoles = new ArrayList<GrantedAuthority>();
            listOfRoles.add(new SimpleGrantedAuthority("ROLE_USER"));
            
            User user = new User("admin", "admin123", listOfRoles);
            
            Authentication auth = new UsernamePasswordAuthenticationToken(user, null);
            
            SecurityContextHolder.getContext().setAuthentication(auth);
            
            System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication().getPrincipal());*/
            
            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);
            client.path("/endclientsearch/execute/privates/pki/wmi")
            .query("lang", "fr")
            .query("mandat", "WV001");
            
            
            assertNotNull(client);

            Response response = client.post(new String("{ \"name\" : \"Carlos N. Esplana\"}"));
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
    
    @Test
    @Ignore
    public void testExecuteNull() {
        try {
            assertNotNull(server);

            WebClient client = WebClient.create(EndPoint.ENDPOINT_ADDRESS.getAddress());
            client.accept(MediaType.APPLICATION_JSON);
            client.type(MediaType.APPLICATION_JSON);
            // client.header("Content-Type", MediaType.APPLICATION_JSON);
            client.path("/endclientsearch/execute");

            assertNotNull(client);

            Response response = client.post(null);
            assertNotNull(response);

            assertEquals(Response.Status.OK.getStatusCode(), displayResponse(response));

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

    @AfterClass
    public static void afterClass() {
        assertNotNull(server);
        server.stop();
        server.destroy();
    }
}
