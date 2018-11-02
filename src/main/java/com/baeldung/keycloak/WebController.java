package com.baeldung.keycloak;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.security.Principal;

import java.sql.*;
import java.util.Properties;

@Controller
public class WebController {

 //   @Autowired
//  private CustomerDAO customerDAO;
	
    /*@GetMapping(path = "/")
    public String index() {
        return "external";
    }*/
    
    @GetMapping(path = "/")
    public String index() {
        return "main_1_page_1";
    }
    
    @GetMapping(path = "/home_eopyy")
    public String home_eopyy(Principal principal, Model model) {
    	//String eopyy_user = principal.getName().toString();
    	model.addAttribute("username", principal.getName());
    	
    	System.out.println(principal.getName().toString());
    	return "home_eopyy";  
    }
    
    @GetMapping(path = "/eopyy")
    public String eopyy(Principal principal, Model model) {
    	String current_user = principal.getName();
    	System.out.println(principal.toString());
    	model.addAttribute("username", current_user);
    	//String AFM_num = getAFM(current_user);
    	if(checkFido(current_user).equals("true")) {
    		return "eopyy_success";
        }
    	
        return "eopyy_fail";
    }

    @GetMapping(path = "/main_page")
    public String customers(Principal principal, Model model) {
    	String current_user = principal.getName();
    	System.out.println(principal.toString());
    	model.addAttribute("username", current_user);
    	//String AFM_num = getAFM(current_user);
    	if(checkFido(current_user).equals("true")) {
    		return "main_page";
        }
    	
        return "main_false";
    }

    // send HTTP request to check if the user is authenticated
    public String checkFido(String username) {

    	String url = "http://localhost:8081/fidouaf/v1/verification/"+username;
		
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
    
   public String getAFM(String username) {
    	
	   try {
           String url = "jdbc:mysql://localhost:3306/eid";
           Connection conn = DriverManager.getConnection(url,"root","root");
           Statement stmt = conn.createStatement();
           ResultSet rs;
           System.out.println("here ok!");
           rs = stmt.executeQuery("SELECT id FROM users WHERE username = \""+ username +"\";");
           while ( rs.next() ) {
               String id = rs.getString("id");
               System.out.println(id);
               return id;
           }
           conn.close();
       } catch (Exception e) {
           System.err.println("Got an exception! ");
           System.err.println(e.getMessage());
           
       }
	   return null;
    	
    }
}
