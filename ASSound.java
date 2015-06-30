package ASUtils;

import java.nio.*;
import java.io.*;

import org.lwjgl.*;
import org.lwjgl.util.*;

import static org.lwjgl.openal.AL10.*;

public class ASSound {
	public ASSound(String path) {
		this();

		filePath = path;

		WaveData wd = null;
		try {
			wd = WaveData.create(new BufferedInputStream(new FileInputStream(
					filePath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		alBufferData(buffer, wd.format, wd.data, wd.samplerate);

		alSourcei(source, AL_BUFFER, buffer);
		alSourcef(source, AL_PITCH, 1);
		alSourcef(source, AL_GAIN, 1);

		alSource3f(source, AL_POSITION, 0, 0, 0);
		alSource3f(source, AL_VELOCITY, 0, 0, 0);
	}
	
	public ASSound(WaveData wd) {
		alBufferData(buffer, wd.format, wd.data, wd.samplerate);

		alSourcei(source, AL_BUFFER, buffer);
		alSourcef(source, AL_PITCH, 1);
		alSourcef(source, AL_GAIN, 1);

		alSource3f(source, AL_POSITION, 0, 0, 0);
		alSource3f(source, AL_VELOCITY, 0, 0, 0);
	}

	public ASSound() {
		source = alGenSources();
		buffer = alGenBuffers();

		position = new float[3];
		velocity = new float[3];
	}

	public void setPosition(int x, int y, int z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;

		FloatBuffer buf = BufferUtils.createFloatBuffer(3);
		buf.put(position);
		buf.flip();

		alSource(source, AL_POSITION, buf);
	}

	public void setVelocity(int vx, int vy, int vz) {
		velocity[0] = vx;
		velocity[1] = vy;
		velocity[2] = vz;

		FloatBuffer buf = BufferUtils.createFloatBuffer(3);
		buf.put(velocity);
		buf.flip();

		alSource(source, AL_VELOCITY, buf);
	}

	public String getFilePath() {
		return filePath;
	}

	public int getSource() {
		return source;
	}

	public int getBuffer() {
		return buffer;
	}

	public void play() {
		alSourcePlay(source);
	}

	public void pause() {
		alSourcePause(source);
	}

	public void stop() {
		alSourceStop(source);
	}

	public void setPitch(float pitch) {
		alSourcef(source, AL_PITCH, pitch);
	}

	public void setGain(float gain) {
		alSourcef(source, AL_GAIN, gain);
	}

	public void deleteSound() {
		alDeleteBuffers(buffer);
		alDeleteSources(source);
	}

	public boolean isPlaying() {
		return (alGetSourcei(source, AL_SOURCE_STATE) == AL_PLAYING);
	}

	private String filePath;
	private int source, buffer;
	private float[] position, velocity;
}
