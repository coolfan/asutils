package ASUtils;

import static java.lang.Math.*;
import static org.lwjgl.openal.AL10.*;

public class ASSoundController {
	public ASSoundController() {
		
	}
	
	public ASSoundController(ASSound sound, int timeLimit, int function) {
		this.sound = sound;
		
		initPitch = alGetSourcef(sound.getSource(), AL_PITCH);
		initGain = alGetSourcef(sound.getSource(), AL_GAIN);
		
		finalPitch = initPitch;
		finalGain = initGain;
		
		isDone = false;
		timerInitialized = false;
		editingPitch = false;
		editingGain = false;
		
		custom = null;
		
		timer = new ASTimer();
		
		time = 0;
		this.timeLimit = timeLimit;
		
		func = function;
	}
	
	public void setCustomFunction(ASSoundControllerCustomFunction a) {
		custom = a;
	}

	public ASSoundControllerCustomFunction getCustomFunction() {
		return custom;
	}
	
	public void setFunction(int function) {
		func = function;
	}
	
	public int getFunction() {
		return func;
	}
	
	public void calculateNext() {
		if (!isDone) {
			if (!timerInitialized) {
				timerInitialized = true;
				timer.initTime();
			}

			int delta = timer.getDelta();
			time += delta;

			if (time >= timeLimit) {
				sound.setPitch(finalPitch);
				sound.setGain(finalGain);
				return;
			}

			if ((func & CUSTOM_FUNCTION) == 0) {

				if (editingPitch) {
					double scaler = 0;

					if ((func & P_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & P_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & P_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & P_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					float newPitch = (float)(scaler * (finalPitch - initPitch)
							+ initPitch);

					sound.setPitch(newPitch);
				}
				
				if (editingGain) {
					double scaler = 0;

					if ((func & G_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & G_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & G_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & G_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					float newGain = (float)(scaler * (finalGain - initGain)
							+ initGain);

					sound.setGain(newGain);
				}
			} else {
				SoundData updated = custom.update(time, timeLimit, new SoundData(initPitch, finalPitch), new SoundData(finalPitch, finalGain));
				
				sound.setPitch(updated.pitch);
				sound.setGain(updated.gain);
			}
			
		}

	}
	
	public boolean isDone() {
		return isDone;
	}
	
	public void setTargetPitch(float pitch) {
		finalPitch = pitch;
		
		editingPitch = true;
	}
	
	public void setTargetGain(float gain) {
		finalGain = gain;
		
		editingGain = true;
	}
	
	public void reset() {
		timer = new ASTimer();
		time = 0;
		timerInitialized = false;
		isDone = false;

		sound.setPitch(initPitch);
		sound.setGain(initGain);;
	}
	
	private float initPitch, finalPitch, initGain, finalGain;
	private ASSound sound;
	
	private boolean isDone, timerInitialized, editingPitch, editingGain;
	
	private ASSoundControllerCustomFunction custom;
	private ASTimer timer;
	
	private int time, timeLimit, func;
	
	public static final int P_SINE = 1 << 0;
	public static final int P_EXPONENTIAL = 1 << 1;
	public static final int P_LOGARITHMIC = 1 << 2;
	public static final int P_LINEAR = 1 << 3;
	
	public static final int G_SINE = 1 << 5;
	public static final int G_EXPONENTIAL = 1 << 6;
	public static final int G_LOGARITHMIC = 1 << 7;
	public static final int G_LINEAR = 1 << 8;
	
	public static final int CUSTOM_FUNCTION = 1 << 30;
	
	public static class SoundData {
		public float pitch;
		public float gain;
		
		public SoundData(float pitch, float gain) {
			this.pitch = pitch;
			this.gain = gain;
		}
	}
}
