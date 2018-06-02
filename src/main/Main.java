/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.security.cert.LDAPCertStoreParameters;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import config.ConfigProcessor;
import main.ldapConnection.Ldap;
import main.parsing.Ecole;
import main.parsing.Utilisateur;
import security.Encrypt;

/**
 *
 * @author eleve
 */
public class Main {

    /**
     * @param args the command line arguments
     * @throws IOException 
     */
	private static int schoolID;
    public static void main(String[] args) throws IOException {

    	 ConfigProcessor properties = ConfigProcessor.getInstance();
 	    	 String serverURL = properties.getPropValues("serverUrl");;
 	    	 String serverPort = properties.getPropValues("serverPort");
 	    	 String serverLogin =properties.getPropValues("serverLogin");
 	    	 String serverPass =properties.getPropValues("serverPass");
 	    	 String DCs =properties.getPropValues("DCs");
 	    	schoolID =Integer.parseInt(properties.getPropValues("EcoleId"));
 	    	System.out.println(schoolID);
 	    	 System.out.println(serverURL);

 	    	 String timeFilter = properties.getPropValues("timeFilter");
 	    	 if (timeFilter==null || timeFilter.equals("")) {
 	    		timeFilter = "20080812000000.0Z";
 	    	 }
 	    	 if (serverURL==null || serverURL.equals("")) {
 	    		 //password
 	    		 Scanner scanner =  new Scanner(System.in);
 	    		 System.out.print("Entrez l'url du LDAP: ");
 	    		 serverURL =  Encrypt.encryptString(scanner.nextLine());
 	    		 scanner.close();
 	    		 properties.setProperty("serverURL",serverURL);
 	    	 }
 	    	 if (serverPort==null || serverPort.equals("")) {
 	    		 //password
 	    		 Scanner scanner =  new Scanner(System.in);
 	    		 System.out.print("Entrez le port du LDAP: ");
 	    		serverPort =  Encrypt.encryptString(scanner.nextLine());
 	    		 scanner.close();
 	    		 properties.setProperty("serverPort",serverPort);
 	    	 }
 	    	 if (serverLogin==null || serverLogin.equals("")) {
 	    		 //password
 	    		 Scanner scanner =  new Scanner(System.in);
 	    		 System.out.print("Entrez le port du LDAP: ");
 	    		serverLogin =  Encrypt.encryptString(scanner.nextLine());
 	    		 scanner.close();
 	    		 properties.setProperty("serverLogin",serverLogin);
 	    	 }
 	    	 if (serverPass==null || serverPass.equals("")) {
 	    		 //password
 	    		 Scanner scanner =  new Scanner(System.in);
 	    		 System.out.print("Entrez le password du LDAP: ");
 	    		 serverPass =  Encrypt.encryptString(scanner.nextLine());
 	    		 scanner.close();
 	    		 properties.setProperty("serverPass",serverPass);
 	    	 }
       
        Ldap ldap = new Ldap(serverURL,serverPort);
        ldap.connect(serverLogin, Encrypt.decryptString(serverPass));
        List<Utilisateur> list = ldap.getUsersToSync(DCs,timeFilter);
        if (ldap.getLastModified()!=null) {
            properties.setProperty("timeFilter",ldap.getLastModified());
        }

        System.out.println(list);
       System.out.println(Encrypt.encryptString("56z1ge45ve51sdfgs"));
        try {
			SendData.syncUsers(list,Encrypt.decryptString(properties.getPropValues("key")));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public static int getSchoolId() {
    	return schoolID;
    }
    


}
