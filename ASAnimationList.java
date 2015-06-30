package ASUtils;

import java.util.*;

public class ASAnimationList implements ASManageable {
	protected Map<String, ASAnimation> animationList;

	public ASAnimationList() {
		super();

		animationList = new HashMap<>();
	}

	@Override
	public void manage() {

		Set<String> dropList = new HashSet<>();

		for (String id : animationList.keySet()) {
			animationList.get(id).calculateNext();

			if (animationList.get(id).isDone()) {
				dropList.add(id);
			}
		}

		for (String id : dropList) {
			animationList.remove(id);
		}
	}

	public void addAnimation(String name, ASAnimation a) {
		animationList.put(name, a);
	}

	public ASAnimation getAnimation(String name) {
		return animationList.get(name);
	}
}
