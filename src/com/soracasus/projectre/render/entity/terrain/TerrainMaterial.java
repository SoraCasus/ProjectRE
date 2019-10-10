package com.soracasus.projectre.render.entity.terrain;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.texture.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class TerrainMaterial {

	private BufferedImage heightMap;
	private Texture textureMap;
	private Texture rTexture;
	private Texture gTexture;
	private Texture bTexture;
	private Texture bgTexture;

	public TerrainMaterial (REFile height, REFile textureMap, REFile rTexture, REFile gTexture, REFile bTexture, REFile bgTexture) {
		if (height != null) {
			try {
				this.heightMap = ImageIO.read(height.getStream());
			} catch (IOException e) {
				e.printStackTrace();
				this.heightMap = null;
			}
		} else {
			heightMap = null;
		}

		this.textureMap = Texture.newTexture(textureMap).create();
		this.rTexture = Texture.newTexture(rTexture).anisotropic().create();
		this.gTexture = Texture.newTexture(gTexture).anisotropic().create();
		this.bTexture = Texture.newTexture(bTexture).anisotropic().create();
		this.bgTexture = Texture.newTexture(bgTexture).anisotropic().create();
	}

	public BufferedImage getHeightMap () {
		return heightMap;
	}

	public Texture getTextureMap () {
		return textureMap;
	}

	public Texture getrTexture () {
		return rTexture;
	}

	public Texture getgTexture () {
		return gTexture;
	}

	public Texture getbTexture () {
		return bTexture;
	}

	public Texture getBgTexture () {
		return bgTexture;
	}
}
