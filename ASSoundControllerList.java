package ASUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ASSoundControllerList implements ASManageable {
	protected Map<String, ASSoundController> soundControllerList;
	
	public ASSoundControllerList() {
		super();
		
		soundControllerList = new HashMap<>();
	}
	
	@Override public void manage() {
		
		Set<String> dropList = new HashSet<>();
		
		for (String id : soundControllerList.keySet()) {
			soundControllerList.get(id).calculateNext();
			
			if (soundControllerList.get(id).isDone()) {
				dropList.add(id);
			}
		}
		
		for (String id : dropList) {
			soundControllerList.remove(id);
		}
	}
	
	public void addSoundController(String name, ASSoundController a) {
		soundControllerList.put(name, a);
	}
	
	public ASSoundController getSoundController(String name) {
		return soundControllerList.get(name);
	}
}
