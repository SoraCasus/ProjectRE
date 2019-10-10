package com.soracasus.projectre.audio;

import com.soracasus.projectre.core.IDisposable;
import com.soracasus.projectre.core.RutikalEngine;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

public class SoundSource implements IDisposable {

	private int sourceID;
	private float rolloffFactor;
	private float referenceDistance;
	private float maxDistance;

	public SoundSource (float rolloffFactor, float referenceDistance, float maxDistance) {
		this.sourceID = AL10.alGenSources();
		this.rolloffFactor = rolloffFactor;
		this.referenceDistance = referenceDistance;
		this.maxDistance = maxDistance;

		AL10.alSourcef(sourceID, AL10.AL_ROLLOFF_FACTOR, rolloffFactor);
		AL10.alSourcef(sourceID, AL10.AL_REFERENCE_DISTANCE, referenceDistance);
		AL10.alSourcef(sourceID, AL10.AL_MAX_DISTANCE, maxDistance);

		RutikalEngine.addDisposable(this);
	}

	public void play() {
		stop();
		AL10.alSourcePlay(sourceID);
	}

	public void setBuffer(SoundBuffer buffer) {
		AL10.alSourcei(sourceID, AL10.AL_BUFFER, buffer.getBufferID());
	}

	public void stop() {
		AL10.alSourceStop(sourceID);
	}

	public void continuePlaying() {
		AL10.alSourcePlay(sourceID);
	}

	public void pause() {
		AL10.alSourcePause(sourceID);
	}

	public boolean isPlaying() {
		return AL10.alGetSourcei(sourceID, AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
	}

	public void setLooping(boolean loop) {
		AL10.alSourcei(sourceID, AL10.AL_LOOPING, loop ? AL10.AL_TRUE : AL10.AL_FALSE);
	}

	public void setVelocity(Vector3f velocity) {
		AL10.alSource3f(sourceID, AL10.AL_VELOCITY, velocity.x, velocity.y, velocity.z);
	}

	public void setVolume(float volume) {
		AL10.alSourcef(sourceID, AL10.AL_GAIN, volume);
	}

	public void setPitch(float pitch) {
		AL10.alSourcef(sourceID, AL10.AL_PITCH, pitch);
	}

	public void setPosition(Vector3f position) {
		setPosition(position.x, position.y, position.z);
	}

	public void setPosition(float x, float y, float z) {
		AL10.alSource3f(sourceID, AL10.AL_POSITION, x, y, z);
	}

	@Override
	public void delete() {
		stop();
		AL10.alDeleteSources(sourceID);
	}

}
