package com.baeldung.keycloak;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.attribute.UserPrincipal;
import java.security.Principal;

import org.apache.commons.logging.Log;
import org.hibernate.mapping.Map;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.common.util.Base64.InputStream;
import org.keycloak.representations.IDToken;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class WebController {

 //   @Autowired
//  private CustomerDAO customerDAO;

    @GetMapping(path = "/")
    public String index() {
        return "external";
    }

    @GetMapping(path = "/main_page")
    public String customers(Principal principal, Model model) {
    	String current_user = principal.getName();
    	System.out.println(principal.toString());
    	model.addAttribute("username", current_user);
    	if(checkFido().equals("true")) {
    		return "main_page";
        }
    	
        return "main_false";
    }

    // send HTTP request to check if the user is authenticated
    public String checkFido() {

    	String url = "http://localhost:8081/fidouaf/v1/stelios/123456789";
		
    	try {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();
    	}
    	catch (Exception e) {
    	return null;
    	}
    }
    
   public void getAFM() {
    	
	   			Keycloak kc = Keycloak.getInstance(
			   "http://localhost:8180/auth",
			   "SpringBootKeycloak", // the realm to log in to
			   "stelios", "Stel.351994",  // the user
	   			"security-admin-console");
    	
    }
}
