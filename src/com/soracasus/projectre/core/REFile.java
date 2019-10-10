package com.soracasus.projectre.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class REFile {

	public String path;
	public String name;
	public String type;

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
		return new BufferedReader(new InputStreamReader(getStream()));
	}

	public String getName () {
		return name;
	}

	public String getPath () {
		return path;
	}

	@Override
	public String toString () {
		return "REFile{" +
				"path='" + path + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
