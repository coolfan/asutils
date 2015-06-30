package ASUtils;

import java.nio.*;
import java.util.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

public class ASSprite extends ASDisplayObject {

	public ASSprite(int x, int y, int width, int height, ASTexture tex,
			int frameWidth, int frameHeight) {
		super(x, y, width, height);
		this.tex = tex;

		spriteLocHandle = glGenBuffers();
		frameTextureCutsHandle = tex.getTextureHandle();

		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		cutListID = new ArrayList<Integer>();

		updateBufferObjectSpriteLoc();
		updateBufferObjectFrameTextureCuts();

		curFrame = 0;
		frameNames = new HashMap<String, Integer>();
		reverseFrameNames = new HashMap<Integer, String>();
	}

	@Override
	public void render() {
		super.render();

		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL_CULL_FACE);

		glTranslated(x, y, 0);
		glRotated(angle, 0, 0, 1);
		glColor4d(r, g, b, alpha);

		tex.bind();

		if (hShader != 0) {
			glUseProgram(hShader);

			int texLoc = glGetUniformLocation(hShader, "texture");
			glUniform1i(texLoc, 0);
		}

		glBindBuffer(GL_ARRAY_BUFFER, spriteLocHandle);
		glVertexPointer(2, GL_INT, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, frameTextureCutsHandle);
		glTexCoordPointer(2, GL_DOUBLE, 0, cutListID.get(curFrame)
				* ASTexture.CUT_SIZE * 8);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glCullFace(GL_FRONT);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glDrawArrays(GL_QUADS, 0, FRAME_SIZE);

		glDisableClientState(GL_TEXTURE_COORD_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);

		glCullFace(GL_BACK);

		if (hShader != 0) {
			glUseProgram(0);
		}

		glColor4d(1, 1, 1, 1);
		glPopAttrib();
		glPopMatrix();
	}

	private void updateBufferObjectSpriteLoc() {
		this.spriteLoc = BufferUtils.createIntBuffer(FRAME_SIZE);
		this.spriteLoc.put(new int[] { (int) ((0 - anchorX) * width),
				(int) ((0 - anchorY) * height), (int) ((1 - anchorX) * width),
				(int) ((0 - anchorY) * height), (int) ((1 - anchorX) * width),
				(int) ((1 - anchorY) * height), (int) ((0 - anchorX) * width),
				(int) ((1 - anchorY) * height) });

		this.spriteLoc.flip();

		glBindBuffer(GL_ARRAY_BUFFER, spriteLocHandle);
		glBufferData(GL_ARRAY_BUFFER, this.spriteLoc, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void updateBufferObjectFrameTextureCuts() {
		int nRows = tex.getHeight() / frameHeight;
		int nCols = tex.getWidth() / frameWidth;

		cutListID.clear();
		for (int i = 1; i * frameHeight <= tex.getHeight(); i++) {
			for (int j = 1; j * frameWidth <= tex.getWidth(); j++) {

				int temp = tex.addTextureCut((j - 1) * frameWidth, (i - 1)
						* frameHeight, (j) * frameWidth, (i) * frameHeight);
				cutListID.add(temp);
			}
		}

		nFrames = nRows * nCols;
	}

	public int getFrameWidth() {
		return frameWidth;
	}

	public int getFrameHeight() {
		return frameHeight;
	}

	public void setFrameSize(int frameWidth, int frameHeight) {
		this.frameWidth = frameWidth;
		this.frameHeight = frameHeight;

		updateBufferObjectFrameTextureCuts();
	}

	public void setCurrentFrame(int curFrame) {
		if (curFrame >= 0 && curFrame < nFrames) {
			this.curFrame = curFrame;
		}

		if (curFrame < 0) {
			this.curFrame = (curFrame % nFrames + nFrames) % nFrames;
		}

		if (curFrame >= nFrames) {
			this.curFrame = curFrame % nFrames;
		}

		if (reverseFrameNames.containsKey(this.curFrame)) {
			curFrameName = reverseFrameNames.get(this.curFrame);
		}

	}

	public void setCurrentFrame(String curFrame) {
		if (frameNames.containsKey(curFrame)) {
			this.curFrame = frameNames.get(curFrame);
		}

		curFrameName = curFrame;
	}

	public int getCurrentFrame() {
		return curFrame;
	}

	public String getCurrentFrameName() {
		return curFrameName;
	}

	public void setFrameNames(Map<String, Integer> frameNames) {
		this.frameNames = frameNames;

		this.reverseFrameNames.clear();
		for (String name : frameNames.keySet()) {
			reverseFrameNames.put(frameNames.get(name), name);
		}
	}

	public void gotoPrevFrame() {
		setCurrentFrame(curFrame - 1);
	}

	public void gotoNextFrame() {
		setCurrentFrame(curFrame + 1);
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);
		updateBufferObjectSpriteLoc();
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);
		updateBufferObjectSpriteLoc();
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		updateBufferObjectSpriteLoc();
	}

	@Override
	public void setAnchorX(double anchorX) {
		super.setAnchorX(anchorX);
		updateBufferObjectSpriteLoc();
	}

	@Override
	public void setAnchorY(double anchorY) {
		super.setAnchorY(anchorY);
		updateBufferObjectSpriteLoc();
	}

	@Override
	public void setAnchors(double anchorX, double anchorY) {
		super.setAnchors(anchorX, anchorY);
		updateBufferObjectSpriteLoc();
	}

	private IntBuffer spriteLoc;

	private static final int FRAME_SIZE = 8;

	private ASTexture tex;

	private int spriteLocHandle;
	private int frameTextureCutsHandle;
	private ArrayList<Integer> cutListID;

	protected int curFrame;
	protected String curFrameName;

	protected Map<String, Integer> frameNames;
	protected Map<Integer, String> reverseFrameNames;

	protected int frameWidth;
	protected int frameHeight;
	protected int nFrames;

}
