package ASUtils;

import java.awt.*;
import java.util.*;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class ASFontManager {
	public ASFontManager() {
		fonts = new HashMap<String, UnicodeFont>();
	}

	public void load(String name, String fontName, int fontStyle, int fontSize) {
		addFont(name, new Font(fontName, fontStyle, fontSize));
	}
	
	public void loadUnicode(String name, String fontName, int fontStyle, int fontSize) {
		addUnicodeFont(name, new Font(fontName, fontStyle, fontSize));
	}

	@SuppressWarnings("unchecked")
	public void addFont(String name, Font font) {
		UnicodeFont f = new UnicodeFont(font);
		f.getEffects().add(new ColorEffect(Color.white));

		f.addAsciiGlyphs();
		try {
			f.loadGlyphs();
		} catch (SlickException e) {
			A.a(e.toString());
		}

		fonts.put(name, f);
	}
	
	@SuppressWarnings("unchecked")
	public void addUnicodeFont(String name, Font font) {
		UnicodeFont f = new UnicodeFont(font);
		f.getEffects().add(new ColorEffect(Color.white));

		f.addGlyphs(32, 65536);
		try {
			f.loadGlyphs();
		} catch (SlickException e) {
			A.a(e.toString());
		}

		fonts.put(name, f);
	}

	public UnicodeFont getFont(String name) {
		return fonts.get(name);
	}

	public UnicodeFont removeFont(String name) {
		return fonts.remove(name);
	}

	public void removeAllFonts() {
		fonts.clear();
	}

	private Map<String, UnicodeFont> fonts;
}
