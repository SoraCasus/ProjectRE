package com.soracasus.projectre.world;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.render.entity.*;
import com.soracasus.projectre.render.model.Model;
import com.soracasus.projectre.render.model.loader.Loader;
import com.soracasus.projectre.render.texture.Texture;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator {

	public static List<Entity> generateWorld(REFile layout, ShipPlayer player) {
	    List<Entity> entities = new ArrayList<>();
	    int[][] walls = new int[30][30];

	    // Parse the file
        try(BufferedReader reader = layout.getReader()) {
            String line;
            int index = 0;
            while((line = reader.readLine()) != null) {
                char[] chars = line.toCharArray();
                for(int i = 0; i < chars.length; i++) {
                    walls[index][i] = Integer.parseInt(String.valueOf(chars[i]));
                }
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < walls.length; i++) {
            for(int j = 0; j < walls[i].length; j++) {

                Vector3f position = new Vector3f(j, 0, i);
                if(walls[j][i] == 1) {
                    // Add a wall
					Wall wall = new Wall(position);
					entities.add(wall);
                } else if(walls[j][i] == 2) {
                    player.getTransform().setPosition(position);
                }

            }
        }

        return entities;
    }

	public static List<Entity> generateEntities (int minX, int maxX, int minY, int maxY, int minZ, int maxZ, float rarity) {

		List<Entity> entities = new ArrayList<>();

		Random random = new Random(System.nanoTime());

		Model rock0 = Loader.load(new REFile("models/rock_01.obj"));
		rock0.material.setDiffuse(Texture.newTexture(new REFile("textures/rock01_diffuse.png")).anisotropic().create());
		rock0.material.setNormal(Texture.newTexture(new REFile("textures/rock01_normal.png")).anisotropic().create());
		rock0.material.setReflectivity(0.5F);
		rock0.material.setShineDamper(32.0F);

		Model rock1 = Loader.load(new REFile("models/rock_02.obj"));
		rock1.material.setDiffuse(Texture.newTexture(new REFile("textures/rock02_diffuse.png")).anisotropic().create());
		rock1.material.setNormal(Texture.newTexture(new REFile("textures/rock02_normal.png")).anisotropic().create());
		rock1.material.setReflectivity(0.5F);
		rock1.material.setShineDamper(32F);

		for (int x = minX; x < maxX; x++) {
			for (int z = minZ; z < maxZ; z++) {

				// Make sure nothing spawns inside the ship

				// if(x >= -10 && x <= 10 && z >= -30 && z <= 30)
					// continue;

				int r = random.nextInt(101);
				if (r <= rarity) {
					// Add a new Entity
					int rockID = random.nextInt(3);
					switch (rockID) {
						case 0: {
							entities.add(new Rock1(new Vector3f(x, random.nextFloat() * 5F, z)));
						}
						break;

						case 1: {
							entities.add(new Rock2(new Vector3f(x, random.nextFloat() * 10F, z)));
						}
						break;

						case 2: {
							entities.add(new Rock3(new Vector3f(x, random.nextFloat() * 10F, z)));
						}
						break;
						default: break;
					}
				} else {
					if (r == 75) {
						entities.add(new Radio(new Vector3f(x, random.nextFloat() * 10, z)));
					}
				}
			}
		}

		entities.add(new Ship(new Vector3f(0, 20, 0)));

		return entities;
	}


}
