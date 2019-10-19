package com.soracasus.projectre.render.model.loader;

import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.util.Utils;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.AIFace;
import org.lwjgl.assimp.AIMesh;
import org.lwjgl.assimp.AIScene;
import org.lwjgl.assimp.AIVector3D;
import org.lwjgl.assimp.Assimp;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class AssimpLoader {

	private static float xDist;
	private static float yDist;
	private static float zDist;

	public static ModelData load (REFile file, boolean flip) {

		xDist = 0F;
		yDist = 0F;
		zDist = 0F;

		final int flags = Assimp.aiProcess_CalcTangentSpace | Assimp.aiProcess_FlipUVs | Assimp.aiProcess_FixInfacingNormals
				| Assimp.aiProcess_Triangulate | Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_GenSmoothNormals | Assimp.aiProcess_GenNormals;
		ByteBuffer buffer = Utils.loadToByteBuffer(file, 1024);
		AIScene scene = Assimp.aiImportFileFromMemory(buffer, flags, file.getType());
		if (scene == null) {
			System.err.println("Failed to load Assimp file: " + file);
			return null;
		}

		PointerBuffer aiMeshes = scene.mMeshes();
		assert (aiMeshes != null);
		AIMesh aiMesh = AIMesh.create(aiMeshes.get(0));
		return processMesh(aiMesh, flip);
	}

	private static ModelData processMesh (AIMesh aiMesh, boolean flip) {
		List<Float> vertices = new ArrayList<>();
		List<Float> textures = new ArrayList<>();
		List<Float> normals = new ArrayList<>();
		List<Float> tangents = new ArrayList<>();
		List<Float> biTangents = new ArrayList<>();
		List<Integer> indices = new ArrayList<>();

		AIVector3D.Buffer buffer;

		buffer = aiMesh.mVertices();
		processVertices(buffer, vertices, flip);

		buffer = aiMesh.mTextureCoords(0);
		processTextures(buffer, textures);

		buffer = aiMesh.mNormals();
		processVector3(buffer, normals, flip);

		buffer = aiMesh.mTangents();
		processVector3(buffer, tangents, flip);

		buffer = aiMesh.mBitangents();
		processVector3(buffer, biTangents, flip);

		processIndices(aiMesh, indices);

		float[] verticesArr = Utils.toFloatArray(vertices);
		float[] texturesArr = Utils.toFloatArray(textures);
		float[] normalsArr = Utils.toFloatArray(normals);
		float[] tangentsArr = Utils.toFloatArray(tangents);
		float[] biTangentsArr = Utils.toFloatArray(biTangents);
		int[] indicesArr = Utils.toIntArray(indices);

		return new ModelData(verticesArr, texturesArr, normalsArr, tangentsArr, biTangentsArr, indicesArr,
				xDist, yDist, zDist);
	}

	private static void processIndices (AIMesh aiMesh, List<Integer> indices) {
		int numFaces = aiMesh.mNumFaces();
		AIFace.Buffer aiFaces = aiMesh.mFaces();
		for (int i = 0; i < numFaces; i++) {
			AIFace aiFace = aiFaces.get(i);
			IntBuffer buffer = aiFace.mIndices();
			while (buffer.remaining() > 0)
				indices.add(buffer.get());
		}
	}

	private static void processVertices (AIVector3D.Buffer aiVectors, List<Float> list, boolean flip) {
		while (aiVectors != null && aiVectors.remaining() > 0) {
			AIVector3D aiVec = aiVectors.get();
			if(flip) {
				list.add(aiVec.x());
				list.add(aiVec.z());
				list.add(-aiVec.y());
				if (Math.abs(aiVec.x()) > xDist)
					xDist = Math.abs(aiVec.x());
				if (Math.abs(aiVec.z()) > yDist)
					yDist = Math.abs(aiVec.z());
				if (Math.abs(-aiVec.y()) > zDist)
					zDist = Math.abs(-aiVec.y());
			} else {
				list.add(aiVec.x());
				list.add(aiVec.y());
				list.add(aiVec.z());
				if (Math.abs(aiVec.x()) > xDist)
					xDist = Math.abs(aiVec.x());
				if (Math.abs(aiVec.y()) > yDist)
					yDist = Math.abs(aiVec.y());
				if (Math.abs(aiVec.z()) > zDist)
					zDist = Math.abs(aiVec.z());
			}
		}
	}

	private static void processVector3 (AIVector3D.Buffer aiVectors, List<Float> list, boolean flip) {
		while (aiVectors != null && aiVectors.remaining() > 0) {
			AIVector3D aiVec = aiVectors.get();
			if (flip) {
				list.add(aiVec.x());
				list.add(aiVec.z());
				list.add(-aiVec.y());
			} else {
				list.add(aiVec.x());
				list.add(aiVec.y());
				list.add(aiVec.z());
			}
		}
	}

	private static void processTextures (AIVector3D.Buffer aiTextures, List<Float> list) {
		while (aiTextures != null && aiTextures.remaining() > 0) {
			AIVector3D aiVec = aiTextures.get();
			list.add(aiVec.x());
			list.add(aiVec.y());
		}
	}

}
