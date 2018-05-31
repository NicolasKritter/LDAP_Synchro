package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

public class ConfigProcessor {
	private InputStream inputStream;
	private static ConfigProcessor configProcessor;
	private static final String PROPERTIES_FILENAME = "config.properties";

	private ConfigProcessor() {

	}

	public static synchronized ConfigProcessor getInstance() {
		if (configProcessor == null) {
			configProcessor = new ConfigProcessor();
		}
		return configProcessor;
	}

	public void setProperty(String key,String password)
			throws FileNotFoundException, IOException {
		Properties prop = new Properties();

		inputStream = getClass().getClassLoader()
				.getResourceAsStream(PROPERTIES_FILENAME);

		if (inputStream != null) {
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException("property file '"
					+ PROPERTIES_FILENAME + "' not found in the classpath");
		}
		prop.load(inputStream);
		prop.setProperty(key, password);
		prop.store(new FileOutputStream("resources/"+PROPERTIES_FILENAME), null);
		inputStream.close();
	}

	public String getPropValues(String propertyName) throws IOException {
		String result = "";
		try {
			Properties prop = new Properties();
			inputStream = getClass().getClassLoader()
					.getResourceAsStream(PROPERTIES_FILENAME);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ PROPERTIES_FILENAME + "' not found in the classpath");
			}

			// get the property value and print it out

			result = prop.getProperty(propertyName);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}

}
