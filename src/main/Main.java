/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.List;

import config.ConfigObject;
import config.ConfigProcessor;
import main.ldapConnection.Ldap;
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
    public static void main(String[] args) throws IOException {
    	ConfigObject.init();       
        Ldap ldap = new Ldap(ConfigObject.getServerURL(),ConfigObject.getServerPort());
        ldap.connect(ConfigObject.getServerLogin(), Encrypt.decryptString(ConfigObject.getServerPass()));
        List<Utilisateur> list = ldap.getUsersToSync(ConfigObject.getDCs(),ConfigObject.getTimeFilter());
        if (ldap.getLastModified()!=null) {
        	ConfigProcessor.getInstance().setProperty("timeFilter",ldap.getLastModified());
        }

        System.out.println(list);
        try {
			SendData.syncUsers(list,Encrypt.decryptString(ConfigObject.getKey()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
  


}
