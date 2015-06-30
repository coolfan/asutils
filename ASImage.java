package ASUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import static org.lwjgl.opengl.GL20.*;

import java.nio.*;

import org.lwjgl.BufferUtils;

public class ASImage extends ASDisplayObject {

	// public

	/**
	 * 
	 * @param name
	 * @param type
	 */
	public ASImage(int x, int y, int width, int height, ASTexture tex) {
		super(x, y, width, height);

		this.tex = tex;

		imageLocHandle = glGenBuffers();

		imageLocBuf = BufferUtils.createIntBuffer(8);
		imageLocBuf.put(new int[] { 0, 0, width, 0, width, height, 0, height });
		imageLocBuf.flip();

		glBindBuffer(GL_ARRAY_BUFFER, imageLocHandle);
		glBufferData(GL_ARRAY_BUFFER, imageLocBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		textureCutHandle = tex.getTextureHandle();
		cutListID = tex.addTextureCut(0, 0, tex.getWidth(), tex.getHeight());
	}

	/**
	 * 
	 * @param left
	 * @param top
	 * @param width
	 * @param height
	 */

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

		glBindBuffer(GL_ARRAY_BUFFER, imageLocHandle);
		glVertexPointer(2, GL_INT, 0, 0);

		glBindBuffer(GL_ARRAY_BUFFER, textureCutHandle);
		glTexCoordPointer(2, GL_DOUBLE, 0, cutListID * ASTexture.CUT_SIZE * 8);

		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glCullFace(GL_FRONT);

		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		glDrawArrays(GL_QUADS, 0, ASTexture.CUT_SIZE);

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

	// private

	public void setTexture(ASTexture tex) {
		this.tex = tex;
	}

	public ASTexture getTexture() {
		return tex;
	}

	public int getImageLocHandle() {
		return imageLocHandle;
	}

	public int getTextureCutHandle() {
		return textureCutHandle;
	}

	public int getCutListID() {
		return cutListID;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
		updateBufferObjectImageLoc();
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
		updateBufferObjectImageLoc();
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		updateBufferObjectImageLoc();
	}

	@Override
	public void setAnchorX(double anchorX) {
		super.setAnchorX(anchorX);
		updateBufferObjectImageLoc();
	}

	@Override
	public void setAnchorY(double anchorY) {
		super.setAnchorY(anchorY);
		updateBufferObjectImageLoc();
	}

	@Override
	public void setAnchors(double anchorX, double anchorY) {
		super.setAnchors(anchorX, anchorY);
		updateBufferObjectImageLoc();
	}

	public void setTextureCut(int xBegin, int yBegin, int xEnd, int yEnd) {
		tex.editCut(cutListID, xBegin, yBegin, xEnd, yEnd);
	}

	private void updateBufferObjectImageLoc() {
		imageLocBuf.clear();
		imageLocBuf.put(new int[] { (int) ((0 - anchorX) * width),
				(int) ((0 - anchorY) * height), (int) ((1 - anchorX) * width),
				(int) ((0 - anchorY) * height), (int) ((1 - anchorX) * width),
				(int) ((1 - anchorY) * height), (int) ((0 - anchorX) * width),
				(int) ((1 - anchorY) * height) });

		imageLocBuf.flip();

		glBindBuffer(GL_ARRAY_BUFFER, imageLocHandle);
		glBufferSubData(GL_ARRAY_BUFFER, 0, imageLocBuf);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private ASTexture tex;

	private int imageLocHandle;
	private int textureCutHandle;

	private int cutListID;

	private IntBuffer imageLocBuf;
}
