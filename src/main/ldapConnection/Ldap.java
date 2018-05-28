package main.ldapConnection;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class Ldap {
	
	 private String serverURL;
	 private String serverPort;
	 private String serverLogin;
	 private String serverPass;
	 private static Ldap ldap; //TODO
	 private Hashtable<String,String> environnement = new Hashtable<String,String>();
	public Ldap(String serverURL, String serverPort) {
		this.serverURL = serverURL;
		this.serverPort = serverPort;
		environnement.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
	    environnement.put(Context.PROVIDER_URL, serverURL + ":" + serverPort);
	}
	
	public void connect(String serverLogin, String serverPass) {
		this.serverLogin = serverLogin;
		this.serverPass = serverPass;
		 environnement.put(Context.SECURITY_AUTHENTICATION, "simple");
	     environnement.put(Context.SECURITY_PRINCIPAL, serverLogin);
	     environnement.put(Context.SECURITY_CREDENTIALS, serverPass);
	}
	
	
	public void getUsers(String DCs) {
		 String typeFilter = "person";
		 try {
			DirContext contexte = new InitialDirContext(environnement);
			SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attributes = {"givenName","memberOf","uid","objectClass","modifyTimeStamp","userPassword" };
    		searchControls.setReturningAttributes(attributes);
            String filter = "(objectClass="+typeFilter+")";
            NamingEnumeration values = contexte.search(DCs, filter, searchControls);
            while (values.hasMoreElements()) {
                SearchResult result = (SearchResult) values.next();
                Attributes attribs = result.getAttributes();
                if (null != attribs) {
                    Attribute userPassword = attribs.get("userPassword");
                    if (userPassword!=null){
                        String pass = new String((byte[]) userPassword.get());
                         System.out.println("pass get: "+pass);
                    }
                    Attribute attName = attribs.get("givenName");
                    if (null!=attName) {
                    	System.out.println("givenName= "+attName);
                    }
                    Attribute lastModified = attribs.get("modifyTimeStamp");
                    if (null!=lastModified) {
                    	System.out.println("modifyTimeStamp= "+lastModified);
                    }
                    Attribute uid = attribs.get("uid");
                    if (null!=uid) {
                    	System.out.println("uid= "+uid.get());
                    }
                   
		}
	}
			contexte.close();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchByType( String typeFilter) {
		//filtrer pour n'avoir que des personnes
      
        //On remplis un tableau avec les parametres d'environement et de connexion au LDAP
      
       
        try {
            //On appelle le contexte à partir de l'environnement
            DirContext contexte = new InitialDirContext(environnement);
           
            
            
            try {
                
                Attributes attrs = contexte.getAttributes("dc=isep,dc=fr");
                //On affiche le nom complet de dupont
                System.out.println(attrs);
                SearchControls searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attributes = { "cn", "sn", "givenName","memberOf","uid","objectClass","modifyTimeStamp","userPassword" };
		searchControls.setReturningAttributes(attributes);
                String filter = "(objectClass="+typeFilter+")";
                NamingEnumeration values = contexte.search("DC=isep,DC=fr", filter, searchControls);
                while (values.hasMoreElements()) {
                    SearchResult result = (SearchResult) values.next();
                    Attributes attribs = result.getAttributes();
                    System.out.println("----");
                    if (null != attribs) {
                       
                        for (NamingEnumeration ae = attribs.getAll(); ae.hasMoreElements();) {
                            Attribute atr = (Attribute) ae.next();
                           
                            String attributeID = atr.getID();
                            if (attributeID.equals("userpassword")){
                                String pwd = new String((byte[]) atr.get());
                                System.out.println("password: "+pwd);
                            }
                         	for (
						Enumeration vals = atr.getAll(); 
						vals.hasMoreElements(); 
						
					){
                                    
                                    System.out.println(attributeID +": "+ vals.nextElement());
                                }
				}
			}
		}
                                contexte.close();

            } catch (NamingException e) {
              
                e.printStackTrace();
            }
        } catch (NamingException e) {
            System.out.println("Connexion au serveur : ECHEC");
            e.printStackTrace();
        }
	}

}
