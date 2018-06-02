package main.ldapConnection;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;

import main.Main;
import main.parsing.Ecole;
import main.parsing.Utilisateur;

public class Ldap {
	
	 private String serverURL;
	 private String serverPort;
	 private String serverLogin;
	 private String serverPass;
	 private static Ldap ldap; 
	 private String lastModified;
	 private Hashtable<String,String> environnement = new Hashtable<String,String>();
	 private static final String PERSON_FILTER =  "person";
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
	
	
	public List<Utilisateur> getUsersToSync(String DCs,String timeFilter) throws IOException {
		int schoolId = Main.getSchoolId();

		 try {
			 List<Utilisateur> list = new LinkedList<Utilisateur>();
			 LdapContext contexte = new InitialLdapContext(environnement,null);
			SearchControls searchControls = new SearchControls();
            searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String[] attributes = {"givenName","memberOf","uid","objectClass","modifyTimeStamp","userPassword","sn" };
    		searchControls.setReturningAttributes(attributes);
    		
    		 String sortKey = "modifyTimeStamp";
    		 contexte.setRequestControls(new Control[] { 
    	         new SortControl(sortKey, Control.CRITICAL) });
    		 // pas de suppérieur ou égal du coup on prend l'inverse: ! de <=
            String filter = "(&(objectClass="+PERSON_FILTER+")(!(modifyTimestamp<="+timeFilter+")))";
            NamingEnumeration values = contexte.search(DCs, filter, searchControls);
            while (values.hasMoreElements()) {
                SearchResult result = (SearchResult) values.next();
                Attributes attribs = result.getAttributes();
                if (null != attribs) {
                    Attribute userPassword = attribs.get("userPassword");
                    Attribute attName = attribs.get("givenName");
                    Attribute lastName = attribs.get("sn");
                    Attribute lastModified = attribs.get("modifyTimeStamp");
                    Attribute uid = attribs.get("uid");
                    if (null!=uid && userPassword!=null) {
                    	Utilisateur utilisateur = new Utilisateur();
                    	utilisateur.setLogin(uid.get().toString());
                    	utilisateur.setPassword( new String((byte[]) userPassword.get()));
                    	System.out.println("uid= "+uid.get());
                    	System.out.println("pass get: "+new String((byte[]) userPassword.get()));
                    	utilisateur.setPrenom(attName.get().toString());
                    	utilisateur.setNom(lastName.get().toString());
                    	utilisateur.setEcole(new Ecole(schoolId));
                    	list.add(utilisateur);
                    	this.lastModified = lastModified.get().toString();
                    	
                    }
                   
                  

                   
		}
	}
            contexte.close();
            return list;
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public String getLastModified() {
		return lastModified;
	}
	public void searchByType( String typeFilter,String DCs) {
		//filtrer pour n'avoir que des personnes
      
        //On remplis un tableau avec les parametres d'environement et de connexion au LDAP
      
       
        try {
            //On appelle le contexte à partir de l'environnement
            DirContext contexte = new InitialDirContext(environnement);
           
            
            
            try {
                
                Attributes attrs = contexte.getAttributes(DCs);
                //On affiche le nom complet de dupont
                System.out.println(attrs);
                SearchControls searchControls = new SearchControls();
                searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                String[] attributes = { "cn", "sn", "givenName","memberOf","uid","objectClass","modifyTimeStamp","userPassword" };
		searchControls.setReturningAttributes(attributes);
                String filter = "(objectClass="+typeFilter+")";
                NamingEnumeration values = contexte.search(DCs, filter, searchControls);
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
