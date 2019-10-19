package com.soracasus.projectre.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class REFile {

	private String path;
	private String name;
	private String type;

	public REFile(String path) {
		String[] dirs = path.split("/");
		this.name = dirs[dirs.length - 1];
		this.path = "/" + path;
		dirs = name.split("\\.");
		this.type = dirs[dirs.length - 1];
	}

	public InputStream getStream() {
		return this.getClass().getResourceAsStream(path);
	}

	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(getStream(), StandardCharsets.UTF_8));
	}

	public String getName () {
		return name;
	}

	public String getPath () {
		return path;
	}

	public String getType() { return type; }

	@Override
	public String toString () {
		return "REFile{" +
				"path='" + path + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
