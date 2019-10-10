package com.soracasus.projectre.util;

import com.soracasus.projectre.core.REFile;
import org.lwjgl.BufferUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;

public class Utils {

	public static <T> T[] toArray (List<T> list) {
		T[] res = (T[]) new Object[list.size()];
		for (int i = 0; i < list.size(); i++)
			res[i] = list.get(i);
		return res;
	}

	public static float[] toFloatArray (List<Float> list) {
		float[] arr = new float[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public static int[] toIntArray (List<Integer> list) {
		int[] arr = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public static List<String> readAllLines (REFile file) {
		List<String> res = new ArrayList<>();
		try (BufferedReader reader = file.getReader()) {
			String line;
			while ((line = reader.readLine()) != null)
				res.add(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	public static ByteBuffer loadToByteBuffer (REFile file, int size) {
		ByteBuffer buffer = null;
		try (InputStream stream = file.getStream();
		     ReadableByteChannel rbc = Channels.newChannel(stream)) {

			buffer = BufferUtils.createByteBuffer(size);

			while (true) {
				int bytes = rbc.read(buffer);
				if (bytes == -1)
					break;
				if (buffer.remaining() == 0)
					buffer = resizeBuffer(buffer, buffer.capacity() * 2);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}


		buffer.flip();
		return buffer;
	}

	private static ByteBuffer resizeBuffer (ByteBuffer buffer, int capacity) {
		ByteBuffer b = BufferUtils.createByteBuffer(capacity);
		buffer.flip();
		b.put(buffer);
		return b;
	}

}
