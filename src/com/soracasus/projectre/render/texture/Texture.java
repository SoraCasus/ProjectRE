package com.soracasus.projectre.render.texture;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.RutikalEngine;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

public class Texture implements IDisposable {

	public final int textureID;
	public final int size;
	private final int type;
	private TextureData data;

	protected Texture (int textureID, TextureData data) {
		this.textureID = textureID;
		this.data = data;
		this.size = data.getWidth();
		this.type = GL11.GL_TEXTURE_2D;
	}

	protected Texture (int textureID, int size, TextureData data) {
		this.textureID = textureID;
		this.size = size;
		this.type = GL11.GL_TEXTURE_2D;
		this.data = data;
		RutikalEngine.addDisposable(this);
	}

	protected Texture (int textureID, int type, int size, TextureData data) {
		this.textureID = textureID;
		this.size = size;
		this.type = type;
		this.data = data;
		RutikalEngine.addDisposable(this);
	}

	public void bindToUnit (int unit) {
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + unit);
		GL11.glBindTexture(type, textureID);
	}

	public static TextureBuilder newTexture(REFile textureFile) {
		return new TextureBuilder(textureFile);
	}

	public static Texture newCubeMap(REFile[] textureFiles) {
		int cubeMapId = TextureUtils.loadCubeMap(textureFiles);
		//TODO needs to know size!
		return new Texture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, 0, null);
	}

	public static Texture newEmptyCubeMap(int size) {
		int cubeMapId = TextureUtils.createEmptyCubeMap(size);
		return new Texture(cubeMapId, GL13.GL_TEXTURE_CUBE_MAP, size, null);
	}

	public TextureData getData () {
		return data;
	}

	@Override
	public void delete () {
		GL11.glDeleteTextures(textureID);
	}
}
