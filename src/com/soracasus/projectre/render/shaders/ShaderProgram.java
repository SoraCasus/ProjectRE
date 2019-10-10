package com.soracasus.projectre.render.shaders;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.RutikalEngine;
import com.soracasus.projectre.render.shaders.uniform.Uniform;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL40;

import java.io.BufferedReader;
import java.io.IOException;

public class ShaderProgram implements IDisposable {

	private int programID;

	public ShaderProgram (REFile shader, String... inVars) {

		RutikalEngine.addDisposable(this);

		ShaderIDs ids = loadShader(shader);

		this.programID = GL20.glCreateProgram();
		if (ids.vertID != -1)
			GL20.glAttachShader(programID, ids.vertID);
		if (ids.geomID != -1)
			GL20.glAttachShader(programID, ids.geomID);
		if (ids.cTessID != -1)
			GL20.glAttachShader(programID, ids.cTessID);
		if (ids.eTessID != -1)
			GL20.glAttachShader(programID, ids.eTessID);
		if (ids.fragID != -1)
			GL20.glAttachShader(programID, ids.fragID);

		bindAttributes(inVars);

		GL20.glLinkProgram(programID);
		checkError(programID, true, GL20.GL_LINK_STATUS);

		if (ids.vertID != -1) {
			GL20.glDetachShader(programID, ids.vertID);
			GL20.glDeleteShader(ids.vertID);
		}

		if (ids.geomID != -1) {
			GL20.glDetachShader(programID, ids.geomID);
			GL20.glDeleteShader(ids.geomID);
		}

		if (ids.cTessID != -1) {
			GL20.glDetachShader(programID, ids.cTessID);
			GL20.glDeleteShader(ids.cTessID);
		}

		if (ids.eTessID != -1) {
			GL20.glDetachShader(programID, ids.eTessID);
			GL20.glDeleteShader(ids.eTessID);
		}

		if (ids.fragID != -1) {
			GL20.glDetachShader(programID, ids.fragID);
			GL20.glDeleteShader(ids.fragID);
		}
	}

	protected void storeUniformLocations (Uniform... uniforms) {
		for (Uniform u : uniforms)
			u.storeUniformLocation(programID);
		GL20.glValidateProgram(programID);
		checkError(programID, true, GL20.GL_VALIDATE_STATUS);
	}

	public void start () {
		GL20.glUseProgram(programID);
	}

	public void stop () {
		GL20.glUseProgram(0);
	}

	private void bindAttributes (String[] inVars) {
		for (int i = 0; i < inVars.length; i++) {
			GL20.glBindAttribLocation(programID, i, inVars[i]);
		}
	}

	private void compileShader (int shaderID, StringBuilder shader) {
		GL20.glShaderSource(shaderID, shader);
		GL20.glCompileShader(shaderID);
		if (checkError(shaderID, false, GL20.GL_COMPILE_STATUS)) {
			String shaderCode = shader.toString();
			String[] lines = shaderCode.split("\\n");
			for(int i = 0; i < lines.length; i++) {
				int lineNumber = i + 1;
				System.out.println(lineNumber + '\t' + lines[i]);
			}
		}
	}

	/**
	 * Checks whether an error has occurred on the given flag
	 *
	 * @param id        The ID of the object to check the error on
	 * @param isProgram Whether the object is a program or not
	 * @param flag      The flag to be checked
	 * @return True if an error has occurred, otherwise false
	 */
	private static boolean checkError (int id, boolean isProgram, int flag) {
		if (isProgram) {
			int res = GL20.glGetProgrami(id, flag);
			if (res != GL11.GL_TRUE) {
				int length = GL20.glGetProgrami(id, GL20.GL_INFO_LOG_LENGTH);
				String log = GL20.glGetProgramInfoLog(id, length);
				System.err.println(log);
				return true;
			}
		} else {
			int res = GL20.glGetShaderi(id, flag);
			if (res != GL11.GL_TRUE) {
				int length = GL20.glGetShaderi(id, GL20.GL_INFO_LOG_LENGTH);
				String log = GL20.glGetShaderInfoLog(id, length);
				System.err.println(log);
				return true;
			}
		}
		return false;
	}

	private void loadInclude (StringBuilder curr, String line) {
		String name = line.substring(9);
		REFile include = new REFile("shaders/include/" + name);

		try (BufferedReader reader = include.getReader()) {
			String l;
			while ((l = reader.readLine()) != null) {
				if (l.startsWith("#include")) {
					loadInclude(curr, l);
					continue;
				}
				curr.append(l).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ShaderIDs loadShader (REFile shader) {
		ShaderIDs ids = new ShaderIDs();

		StringBuilder vertSrc = new StringBuilder();
		StringBuilder geomSrc = new StringBuilder();
		StringBuilder cTessSrc = new StringBuilder();
		StringBuilder eTessSrc = new StringBuilder();
		StringBuilder fragSrc = new StringBuilder();
		StringBuilder current = null;

		try (BufferedReader reader = shader.getReader()) {
			String line;
			while ((line = reader.readLine()) != null) {
				// Check shader type
				if (line.startsWith("#shader")) {
					if (line.startsWith("#shader vertex")) {
						current = vertSrc;
					} else if (line.startsWith("#shader fragment")) {
						current = fragSrc;
					} else if (line.startsWith("#shader geometry")) {
						current = geomSrc;
					} else if (line.startsWith("#shader tess_control")) {
						current = cTessSrc;
					} else if (line.startsWith("#shader tess_eval")) {
						current = eTessSrc;
					} else {
						System.err.println("Shader Error: Unrecognized preprocessor: " + line);
					}
					continue;
				}

				if (line.startsWith("#include")) {
					// Load include file
					loadInclude(current, line);
					continue;
				}

				if (current == null) {
					System.err.println("Improper shader formatting! \n No shader selected, make sure #shader preprocessor is properly set!");
					return ids;
				}

				current.append(line).append("\n");

			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Generate ShaderIDs
		if (vertSrc.length() > 0) {
			ids.vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
			compileShader(ids.vertID, vertSrc);
		}

		if (geomSrc.length() > 0) {
			ids.geomID = GL20.glCreateShader(GL32.GL_GEOMETRY_SHADER);
			compileShader(ids.geomID, geomSrc);
		}

		if (cTessSrc.length() > 0) {
			ids.cTessID = GL20.glCreateShader(GL40.GL_TESS_CONTROL_SHADER);
			compileShader(ids.cTessID, cTessSrc);
		}

		if (eTessSrc.length() > 0) {
			ids.eTessID = GL20.glCreateShader(GL40.GL_TESS_EVALUATION_SHADER);
			compileShader(ids.eTessID, eTessSrc);
		}

		if (fragSrc.length() > 0) {
			ids.fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
			compileShader(ids.fragID, fragSrc);
		}

		return ids;
	}

	@Override
	public void delete () {
		GL20.glDeleteProgram(programID);
	}

	private static class ShaderIDs {
		int vertID = -1;
		int geomID = -1;
		int cTessID = -1;
		int eTessID = -1;
		int fragID = -1;
	}

}
