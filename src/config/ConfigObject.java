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

		serverURL = properties.getPropValues("serverUrl");
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
		if (timeFilter == null || timeFilter.equals("")) {
			timeFilter = "20080812000000.0Z";
		}
		if (serverURL == null || serverURL.equals("")) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrez l'url du LDAP: ");
			serverURL = Encrypt.encryptString(scanner.nextLine());
			scanner.close();
			properties.setProperty("serverURL", serverURL);
		}
		if (serverPort == null || serverPort.equals("")) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrez le port du LDAP: ");
			serverPort = Encrypt.encryptString(scanner.nextLine());
			scanner.close();
			properties.setProperty("serverPort", serverPort);
		}
		if (serverLogin == null || serverLogin.equals("")) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrez le port du LDAP: ");
			serverLogin = Encrypt.encryptString(scanner.nextLine());
			scanner.close();
			properties.setProperty("serverLogin", serverLogin);
		}
		if (serverPass == null || serverPass.equals("")) {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrez le password du LDAP: ");
			serverPass = Encrypt.encryptString(scanner.nextLine());
			scanner.close();
			properties.setProperty("serverPass", serverPass);
		}
	}
}
