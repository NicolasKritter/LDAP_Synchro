/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import main.ldapConnection.Ldap;

/**
 *
 * @author eleve
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	 //Adresse du serveur sur lequel se trouve l'annuaire LDAP
        String serverURL = "ldap://localhost";
        //Pourt du serveur sur lequel se trouve l'annuaire LDAP
        String serverPort = "1389";
        //Login de connexion à l'annuaire LDAP : Le login dois être sous forme de "distinguished name"
        //ce qui signifie qu'il dois être affiché sous la forme de son arborescence LDAP
        String serverLogin = "cn=Directory Manager";
        //Mot de passe de connexion à l'annuaire LDAP
        String serverPass = "password";
		String DCs = "DC=isep,DC=fr";

        Ldap ldap = new Ldap(serverURL,serverPort);
        ldap.connect(serverLogin,serverPass);
        ldap.getUsers(DCs);
        ldap.searchByType("person");
    	

       
    }

}
