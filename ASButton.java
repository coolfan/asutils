package ASUtils;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.*;
import org.lwjgl.*;

import java.util.*;
import java.nio.*;

import org.lwjgl.input.*;
import org.lwjgl.opengl.Display;

public class ASButton extends ASDisplayObject {
	public ASButton() {
		super(0, 0, 0, 0);
	}

	public ASButton(int x, int y, int width, int height, ASTexture tex,
			boolean isHorizontalCut) {
		super(x, y, width, height);
		this.tex = tex;

		if (isHorizontalCut) {
			sprite = new ASSprite(x, y, width, height, tex, tex.getWidth() / 3,
					tex.getHeight());
		} else {
			sprite = new ASSprite(x, y, width, height, tex, tex.getWidth(),
					tex.getHeight() / 3);
		}

		frameNames = new HashMap<String, Integer>();
		frameNames.put("up", 0);
		frameNames.put("over", 1);
		frameNames.put("down", 2);

		sprite.setFrameNames(frameNames);
	}

	public ASTexture getTexture() {
		return tex;
	}

	public void setTexture(ASTexture tex, boolean isHorizontalCut) {
		this.tex = tex;

		if (isHorizontalCut) {
			sprite = new ASSprite(x, y, width, height, tex, tex.getWidth() / 3,
					tex.getHeight());
		} else {
			sprite = new ASSprite(x, y, width, height, tex, tex.getWidth(),
					tex.getHeight() / 3);
		}

		frameNames = new HashMap<String, Integer>();
		frameNames.put("up", 0);
		frameNames.put("over", 1);
		frameNames.put("down", 2);

		sprite.setFrameNames(frameNames);
		wasMouseDown = false;
	}

	public ASListener getListener() {
		return listener;
	}

	public void setListener(ASListener listener) {
		this.listener = listener;
	}

	public ASEventQueue getEventQueue() {
		return eventQueue;
	}

	public void setEventQueue(ASEventQueue eventQueue) {
		this.eventQueue = eventQueue;
	}

	@Override
	public void render() {
		glPushMatrix();
		glTranslated(x, y, 0);
		glRotated(angle, 0, 0, 1);

		sprite.render();

		FloatBuffer mvBuf = BufferUtils.createFloatBuffer(16);
		glGetFloat(GL_MODELVIEW_MATRIX, mvBuf);

		Matrix4f modelview = new Matrix4f();
		modelview.load(mvBuf);
		modelview.invert();

		Vector4f mouse = new Vector4f(Mouse.getX(), Display.getHeight()
				- Mouse.getY() - 1, 0, 1);

		Matrix4f.transform(modelview, mouse, mouse);

		boolean leftButtonDown = Mouse.isButtonDown(0);

		if (!leftButtonDown && mouse.getX() >= 0 && mouse.getX() <= width
				&& mouse.getY() >= 0 && mouse.getY() <= height) {
			sprite.setCurrentFrame("over");

		} else if (leftButtonDown && mouse.getX() >= 0 && mouse.getX() <= width
				&& mouse.getY() >= 0 && mouse.getY() <= height) {
			wasMouseDown = true;
			sprite.setCurrentFrame("down");
		} else {
			sprite.setCurrentFrame("up");
		}

		if (wasMouseDown && !leftButtonDown && mouse.getX() >= 0
				&& mouse.getX() <= width && mouse.getY() >= 0
				&& mouse.getY() <= height) {
			if (eventQueue != null && listener != null) {
				eventQueue.addListener(listener);
			}
			wasMouseDown = false;
		}

		glPopMatrix();
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		sprite.setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		sprite.setHeight(height);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		sprite.setSize(width, height);
	}

	@Override
	public void setColor(double r, double g, double b) {
		super.setColor(r, g, b);
		sprite.setColor(r, g, b);
	}

	@Override
	public void setAlpha(double alpha) {
		super.setAlpha(alpha);
		sprite.setAlpha(alpha);
	}

	@Override
	public void setAnchorX(double anchorX) {
		super.setAnchorX(anchorX);
		sprite.setAnchorX(anchorX);
	}

	@Override
	public void setAnchorY(double anchorY) {
		super.setAnchorY(anchorY);
		sprite.setAnchorY(anchorY);
	}
	
	public ASDisplayObject getObjectToAnimate() {
		return sprite;
	}

	@Override
	public void setShaderProgram(int hShader) {
		super.setShaderProgram(hShader);
		sprite.setShaderProgram(hShader);
	}

	private ASTexture tex;
	private ASSprite sprite;
	private ASListener listener;
	private ASEventQueue eventQueue;

	private Map<String, Integer> frameNames;
	private boolean wasMouseDown;
}
