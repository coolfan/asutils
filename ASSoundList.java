package ASUtils;

import java.util.*;

public class ASSoundList {
	public ASSoundList() {
		map = new HashMap<>();
	}
	
	public void load(String name, String path) {
		map.put(name, new ASSound(path));
	}
	
	public void addSound(String name, ASSound sound) {
		map.put(name, sound);
	}
	
	public void playSound(String name) {
		map.get(name).play();
	}
	
	public void deleteSound(String name) {
		ASSound s = map.remove(name);
		s.deleteSound();
	}
	
	public void deleteAllSounds() {
		for (ASSound s : map.values()) {
			s.deleteSound();
		}
		
		map.clear();
	}
	
	private Map<String, ASSound> map;
}
