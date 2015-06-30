package ASUtils;

import org.lwjgl.Sys;

public class ASTimer {

	public void initTime() {
		time = Sys.getTime();
	}

	public int getDelta() {
		long retValL = Sys.getTime() - time;
		time = Sys.getTime();

		return (int) retValL;
	}

	public void addTime(int deltaTime) {
		time += deltaTime;
	}

	private long time;
}
