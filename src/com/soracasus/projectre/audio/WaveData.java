package com.soracasus.projectre.audio;

import com.soracasus.projectre.core.REFile;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL10;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class WaveData {

	public final int format;
	public final int samplerate;
	public final int totalBytes;
	public final int bytesPerFrame;
	public final ByteBuffer data;

	private final AudioInputStream audioStream;
	private final byte[] dataArray;

	private WaveData(AudioInputStream stream) {
		this.audioStream = stream;
		AudioFormat audioFormat = stream.getFormat();
		format = getOpenAlFormat(audioFormat.getChannels(), audioFormat.getSampleSizeInBits());
		this.samplerate = (int) audioFormat.getSampleRate();
		this.bytesPerFrame = audioFormat.getFrameSize();
		this.totalBytes = (int) (stream.getFrameLength() * bytesPerFrame);
		this.data = BufferUtils.createByteBuffer(totalBytes);
		this.dataArray = new byte[totalBytes];
		loadData();
	}

	protected void dispose() {
		try {
			audioStream.close();
			data.clear();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ByteBuffer loadData() {
		try {
			int bytesRead = audioStream.read(dataArray, 0, totalBytes);
			data.clear();
			data.put(dataArray, 0, bytesRead);
			data.flip();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Couldn't read bytes from audio stream!");
		}
		return data;
	}


	public static WaveData create(REFile file){
		InputStream stream = file.getStream();
		if(stream==null){
			System.err.println("Couldn't find file: "+file);
			return null;
		}
		InputStream bufferedInput = new BufferedInputStream(stream);
		AudioInputStream audioStream = null;
		try {
			audioStream = AudioSystem.getAudioInputStream(bufferedInput);
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		assert(audioStream != null);
		return new WaveData(audioStream);
	}


	private static int getOpenAlFormat(int channels, int bitsPerSample) {
		if (channels == 1) {
			return bitsPerSample == 8 ? AL10.AL_FORMAT_MONO8 : AL10.AL_FORMAT_MONO16;
		} else {
			return bitsPerSample == 8 ? AL10.AL_FORMAT_STEREO8 : AL10.AL_FORMAT_STEREO16;
		}
	}

}
