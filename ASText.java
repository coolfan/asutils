package ASUtils;

import org.lwjgl.opengl.GL31;
import org.newdawn.slick.*;

import static org.lwjgl.opengl.GL11.*;

public class ASText extends ASDisplayObject {

	public ASText(UnicodeFont font, String text) {
		this.font = font;
		this.text = text;
	}

	public UnicodeFont getFont() {
		return font;
	}

	public void setFont(UnicodeFont font) {
		this.font = font;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public int getWidth() {
		return font.getWidth(text);
	}
	
	public int getHeight() {
		return font.getHeight(text);
	}

	@Override
	public void render() {
		glPushAttrib(GL_ENABLE_BIT);
		glDisable(GL31.GL_TEXTURE_RECTANGLE);
		font.drawString(x, y, text, new Color((float) r, (float) g, (float) b,
				(float) alpha));
		glPopAttrib();
	}

	private UnicodeFont font;
	private String text;

}
