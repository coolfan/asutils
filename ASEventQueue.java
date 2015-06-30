package ASUtils;

import java.util.*;

public class ASEventQueue implements ASManageable {
	public ASEventQueue() {
		eventQueue = new ArrayList<ASListener>();
	}

	public void addListener(ASListener listener) {
		eventQueue.add(listener);
	}

	public void execute() {
		for (ASListener listener : eventQueue) {
			listener.execute();
		}

		eventQueue.clear();
	}
	
	@Override
	public void manage() {
		execute();
	}

	ArrayList<ASListener> eventQueue;
}
