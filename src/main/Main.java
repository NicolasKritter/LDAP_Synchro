/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import config.ConfigProcessor;
import main.ldapConnection.Ldap;
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
    public static void main(String[] args) throws IOException {

    	 ConfigProcessor properties = ConfigProcessor.getInstance();
 	    	String serverURL = properties.getPropValues("serverUrl");;
 	    	 String serverPort = properties.getPropValues("serverPort");
 	    	 String serverLogin =properties.getPropValues("serverLogin");
 	    	 String serverPass =properties.getPropValues("serverPass");
 	    	 String DCs =properties.getPropValues("DCs");
 	    	 System.out.println(serverURL);
 	    	 if (serverPass==null || serverPass.equals("")) {
 	    		 //password
 	    		 Scanner scanner =  new Scanner(System.in);
 	    		 System.out.print("Entrez le password dy LDAP: ");
 	    		 serverPass =  Encrypt.encryptString(scanner.nextLine());
 	    		 scanner.close();
 	    		 properties.setProperty("serverPass",serverPass);
 	    	 }
       
        Ldap ldap = new Ldap(serverURL,serverPort);
//        ldap.connect(serverLogin,serverPass);
//        ldap.getUsers(DCs);
//        ldap.searchByType("person");

       String decrypted = Encrypt.decryptString(serverPass);
       System.out.println(decrypted);
       

        

    }
    


}
