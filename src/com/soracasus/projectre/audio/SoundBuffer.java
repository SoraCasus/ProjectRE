package com.soracasus.projectre.audio;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.REFile;
import com.soracasus.projectre.core.RutikalEngine;
import org.lwjgl.openal.AL10;

public class SoundBuffer implements IDisposable {

	private int bufferID;

	public SoundBuffer(REFile audioFile) {
		this.bufferID = AL10.alGenBuffers();
		WaveData data = WaveData.create(audioFile);
		assert (data != null);
		AL10.alBufferData(bufferID, data.format, data.data, data.samplerate);
		data.dispose();

		RutikalEngine.addDisposable(this);
	}

	@Override
	public void delete () {
		AL10.alDeleteBuffers(bufferID);
	}

	public int getBufferID () {
		return bufferID;
	}
}
