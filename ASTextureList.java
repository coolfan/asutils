package ASUtils;

import java.util.*;

public class ASTextureList implements ASManageable {
	private Map<String, ASTexture> texList;

	public ASTextureList() {
		texList = new HashMap<String, ASTexture>();
	}

	public void addTexture(String name, ASTexture tex) {
		texList.put(name, tex);
	}

	public void removeTexture(String name) {
		texList.get(name).delete();
		texList.remove(name);
	}

	public ASTexture getTexture(String name) {
		return texList.get(name);
	}

	public void removeAllTextures() {
		for (ASTexture name : texList.values()) {
			name.delete();
		}
		texList.clear();
	}

	public void compile() {
		for (ASTexture tex : texList.values()) {
			tex.makeBuffer();
		}
	}
	
	@Override
	public void manage() {
		compile();
	}

	public void load(String name, String path) {
		texList.put(name, new ASTexture(path));
	}

	public void load(String name, String path, int filter) {
		texList.put(name, new ASTexture(path, filter));
	}

}
