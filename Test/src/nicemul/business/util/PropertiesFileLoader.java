package nicemul.business.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

public class PropertiesFileLoader {

	/**
	 * @param filename
	 *            A complete filename
	 * @return True if the file is a properties file
	 */
	public static boolean isPropertiesFile(String filename) {
		String extension = StringUtils.substringAfter(filename, ".");
		return extension.equals("properties");
	}

	public static Properties loadPropertiesFile(File propsFile) throws IOException {
		Properties prop = new Properties();
		FileInputStream in;
		in = new FileInputStream(propsFile);
		prop.load(in);
		in.close();
		return prop;
	}

}
