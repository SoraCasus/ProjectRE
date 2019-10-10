package com.soracasus.projectre.audio;

import com.soracasus.projectre.core.REFile;
import org.joml.Vector3f;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class AudioEngine {

	private static long device;
	private static long context;

	public static SoundBuffer shipSound;
	public static SoundBuffer bgMusic;
	public static SoundBuffer crashSound;
	public static SoundBuffer[] radioLog;

	public static void init() {
		device = ALC10.alcOpenDevice((ByteBuffer) null);
		if(device == MemoryUtil.NULL)
			return;

		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		if(!deviceCaps.OpenALC10)
			return;

		context = ALC10.alcCreateContext(device, (IntBuffer)null);
		ALC10.alcMakeContextCurrent(context);
		AL.createCapabilities(deviceCaps);

		radioLog = new SoundBuffer[10];

		shipSound = new SoundBuffer(new REFile("audio/ship.wav"));
		bgMusic = new SoundBuffer(new REFile("audio/bgmusic.wav"));
		crashSound = new SoundBuffer(new REFile("audio/crash.wav"));
		for(int i = 0; i < radioLog.length; i++)
			radioLog[i] = new SoundBuffer(new REFile("audio/Radio_" + (i + 1) + ".wav"));
	}

	public static void setListenerData(Vector3f position) {
		AL10.alListener3f(AL10.AL_POSITION, position.x, position.y, position.z);
		AL10.alListener3f(AL10.AL_VELOCITY, 0, 0, 0);
	}

	public static void setDistanceModel(int distanceModel) {
		AL10.alDistanceModel(distanceModel);
	}

	public static void delete() {
		ALC10.alcMakeContextCurrent(MemoryUtil.NULL);
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}



}
