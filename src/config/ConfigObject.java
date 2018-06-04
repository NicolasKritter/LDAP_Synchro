package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import security.Encrypt;

public class ConfigObject {
	private static ConfigProcessor properties = ConfigProcessor.getInstance();
	private static String serverURL;
	private static String serverPort;
	private static String serverLogin;
	private static String serverPass;
	private static String DCs;
	private static int schoolID;
	private static String timeFilter;
	private static String key;

	public static ConfigProcessor getProperties() {
		return properties;
	}

	public static String getServerURL() {
		return serverURL;
	}

	public static String getServerPort() {
		return serverPort;
	}

	public static String getServerLogin() {
		return serverLogin;
	}

	public static String getServerPass() {
		return serverPass;
	}

	public static String getDCs() {
		return DCs;
	}

	public static int getSchoolID() {
		return schoolID;
	}

	public static String getTimeFilter() {
		return timeFilter;
	}

	public static String getKey() {
		return key;
	}
	public static void init() throws IOException {

		serverURL = properties.getPropValues("serverURL");
		serverPort = properties.getPropValues("serverPort");
		serverLogin = properties.getPropValues("serverLogin");
		serverPass = properties.getPropValues("serverPass");
		DCs = properties.getPropValues("DCs");
		timeFilter = properties.getPropValues("timeFilter");
		schoolID = Integer.parseInt(properties.getPropValues("EcoleId"));
		key = properties.getPropValues("key");
		updateValues();
	}

	public static void updateValues()
			throws FileNotFoundException, IOException {
		Scanner scanner = new Scanner(System.in);
		if (timeFilter == null || timeFilter.equals("")) {
			timeFilter = "20080812000000.0Z";
		}
		if (serverURL == null || serverURL.equals("")) {
			
			System.out.print("Entrez l'url du LDAP: ");
			serverURL = scanner.nextLine();
			
			properties.setProperty("serverURL", serverURL);
		}
		if (serverPort == null || serverPort.equals("")) {
			
			System.out.print("Entrez le port du LDAP: ");
			serverPort = scanner.nextLine();
			
			properties.setProperty("serverPort", serverPort);
		}
		if (DCs == null || DCs.equals("")) {
			
			System.out.print("Entrez le domaine du LDAP: ");
			DCs = scanner.nextLine();
			properties.setProperty("DCs", DCs);
		}
		if (serverLogin == null || serverLogin.equals("")) {
			
			System.out.print("Entrez le login du LDAP: ");
			serverLogin =scanner.nextLine();
			properties.setProperty("serverLogin", serverLogin);
		}
		if (serverPass == null || serverPass.equals("")) {
			
			System.out.print("Entrez le password du LDAP: ");
			serverPass = Encrypt.encryptString(scanner.nextLine());
			
			properties.setProperty("serverPass", serverPass);
		}
		scanner.close();
	}
}
