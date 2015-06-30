package ASUtils;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.util.vector.Matrix4f;

import static org.lwjgl.openal.AL10.*;

public class ASSoundListener {
	public ASSoundListener() {
		position = new float[3];
		velocity = new float[3];
		orientation = new float[6];
	}
	
	public void setPosition(float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
		
		FloatBuffer buf = BufferUtils.createFloatBuffer(3);
		buf.put(position);
		buf.flip();
		
		alListener(AL_POSITION, buf);
	}
	
	public void setVelocity(float vx, float vy, float vz) {
		velocity[0] = vx;
		velocity[1] = vy;
		velocity[2] = vz;
		
		FloatBuffer buf = BufferUtils.createFloatBuffer(3);
		buf.put(velocity);
		buf.flip();
		
		alListener(AL_VELOCITY, buf);
	}
	
	public void setOrientation(float atX, float atY, float atZ, float upX, float upY, float upZ) {
		orientation[0] = atX;
		orientation[1] = atY;
		orientation[2] = atZ;
		orientation[3] = upX;
		orientation[4] = upY;
		orientation[5] = upZ;
		
		FloatBuffer buf = BufferUtils.createFloatBuffer(6);
		buf.put(orientation);
		buf.flip();
		
		alListener(AL_ORIENTATION, buf);
	}
	
	private float[] position;
	private float[] velocity;
	private float[] orientation;
}
