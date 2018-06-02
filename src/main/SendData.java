package main;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import main.parsing.Utilisateur;

public class SendData {
private static final String HOST = "http://localhost:8080/API/LDAP-synchro";
private static final String ADD_UTILISATEURS = "/ajouter-utilisateur";

public static void syncUsers(List<Utilisateur> list,String KEY ) throws Exception {
	String urlBuild = HOST+ADD_UTILISATEURS+"/"+KEY;
	URL url = new URL(urlBuild);
	HttpURLConnection con = (HttpURLConnection) url.openConnection();
	con.setRequestMethod("POST");
	con.setRequestProperty("Content-Type", "application/json");
	String data = list.toString();
	con.setDoOutput(true);
	DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	wr.writeBytes(data);
	wr.flush();
	wr.close();

	int responseCode = con.getResponseCode();
	System.out.println(responseCode);
}
}
