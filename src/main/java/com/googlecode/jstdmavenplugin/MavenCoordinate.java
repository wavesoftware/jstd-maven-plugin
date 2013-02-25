package com.googlecode.jstdmavenplugin;

public interface MavenCoordinate {
	public String getGroupId();
	public String getArtifactId();
	public String getVersion();
	public String getFileType();
}
