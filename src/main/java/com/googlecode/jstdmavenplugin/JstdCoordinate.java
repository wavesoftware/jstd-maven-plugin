package com.googlecode.jstdmavenplugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class JstdCoordinate implements MavenCoordinate {

	private static final String PROPERTIES_FILE = "jstdCoordinate.properties";

	private final String groupId;
	private final String artifactId;
	private final String version;

	public JstdCoordinate() throws IOException {
		Properties props = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE);
		if (inputStream == null) {
			throw new FileNotFoundException("property file '" + PROPERTIES_FILE + "' not found in the classpath");
		}
		props.load(inputStream);

		groupId = props.getProperty("jstd.groupId");
		artifactId = props.getProperty("jstd.artifactId");
		version = props.getProperty("jstd.version");
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public String getFileType() {
		return "jar";
	}
}
