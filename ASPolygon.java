package ASUtils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL31.*;

import org.lwjgl.*;

import java.nio.*;

public class ASPolygon extends ASDisplayObject {

	protected int nVertices;
	protected int[] vertices;

	protected IntBuffer vertBuf;
	protected int hVert;

	public ASPolygon(int[] vertices) {
		this.vertices = vertices;
		nVertices = vertices.length / 2;

		int[] extremes = { Integer.MAX_VALUE, Integer.MAX_VALUE,
				Integer.MIN_VALUE, Integer.MIN_VALUE };
		for (int i = 0; i < vertices.length; i += 2) {
			extremes[0] = (vertices[i] < extremes[0]) ? vertices[i]
					: extremes[0];
			extremes[1] = (vertices[i + 1] < extremes[1]) ? vertices[i + 1]
					: extremes[1];
			extremes[2] = (vertices[i] > extremes[2]) ? vertices[i]
					: extremes[2];
			extremes[3] = (vertices[i + 1] > extremes[3]) ? vertices[i + 1]
					: extremes[3];
		}

		this.x = extremes[0];
		this.y = extremes[1];

		this.width = extremes[2] - extremes[0];
		this.height = extremes[3] - extremes[1];

		for (int i = 0; i < this.vertices.length; i += 2) {
			this.vertices[i] -= x;
			this.vertices[i + 1] -= y;
		}

		makeVBO();
	}

	@Override
	public void render() {
		super.render();

		glPushMatrix();
		glPushAttrib(GL_ENABLE_BIT);
		// glEnable(GL_CULL_FACE);

		// glEnable(GL_TEXTURE_RECTANGLE);
		glBindTexture(GL_TEXTURE_RECTANGLE, 0);

		// glEnable(GL_TEXTURE_2D);
		glBindTexture(GL_TEXTURE_2D, 0);

		glColor4d(r, g, b, alpha);
		glTranslated(x, y, 0);
		glRotated(angle, 0, 0, 1);

		// glCullFace(GL_FRONT);
		glBindBuffer(GL_ARRAY_BUFFER, hVert);
		glVertexPointer(2, GL_INT, 0, 0L);
		glBindBuffer(GL_ARRAY_BUFFER, 0);

		glEnableClientState(GL_VERTEX_ARRAY);
		glDrawArrays(GL_POLYGON, 0, vertices.length);
		glDisableClientState(GL_VERTEX_ARRAY);
		// glCullFace(GL_BACK);

		glColor4d(1, 1, 1, 1);

		// glCullFace(GL_BACK);
		glPopAttrib();
		glPopMatrix();
	}

	@Override
	public void setAnchorX(double anchorX) {
		double oldAnchorX = this.anchorX;
		super.setAnchorX(anchorX);

		int xAdjustment = (int) Math.round((oldAnchorX - anchorX) * width);
		for (int i = 0; i < vertices.length; i += 2) {
			vertices[i] += xAdjustment;
		}

		editVBO();
	}

	@Override
	public void setAnchorY(double anchorY) {
		double oldAnchorY = this.anchorY;
		super.setAnchorY(anchorY);

		int yAdjustment = (int) Math.round((oldAnchorY - anchorY) * height);
		for (int i = 1; i < vertices.length; i += 2) {
			vertices[i] += yAdjustment;
		}

		editVBO();
	}

	@Override
	public void setAnchors(double anchorX, double anchorY) {
		double oldAnchorX = this.anchorX;
		double oldAnchorY = this.anchorY;
		super.setAnchors(anchorX, anchorY);

		int xAdjustment = (int) Math.round((oldAnchorX - anchorX) * width);
		int yAdjustment = (int) Math.round((oldAnchorY - anchorY) * height);
		for (int i = 0; i < vertices.length; i += 2) {
			vertices[i] += xAdjustment;
			vertices[i + 1] += yAdjustment;
		}

		editVBO();
	}

	@Override
	public void setWidth(int width) {
		double factor = (double) width / this.width;
		super.setWidth(width);

		double oldAnchorX = anchorX;

		setAnchorX(0);

		for (int i = 0; i < vertices.length; i += 2) {
			vertices[i] *= factor;
		}

		setAnchorX(oldAnchorX);

		editVBO();
	}

	@Override
	public void setHeight(int height) {
		double factor = (double) height / this.height;
		super.setHeight(height);

		double oldAnchorY = anchorY;

		setAnchorY(0);

		for (int i = 1; i < vertices.length; i += 2) {
			vertices[i] *= factor;
		}

		setAnchorY(oldAnchorY);

		editVBO();
	}

	public int[] getVertices() {
		return vertices;
	}

	public void setVertices(int[] vertices) {
		this.vertices = vertices;

		editVBO();
	}

	@Override
	public void setSize(int width, int height) {
		double xFactor = (double) width / this.width;
		double yFactor = (double) height / this.height;

		double oldAnchorX = anchorX;
		double oldAnchorY = anchorY;

		setAnchors(0, 0);

		for (int i = 0; i < vertices.length; i += 2) {
			vertices[i] *= xFactor;
			vertices[i + 1] *= yFactor;
		}

		setAnchors(oldAnchorX, oldAnchorY);

		editVBO();
	}

	private void makeVBO() {
		vertBuf = BufferUtils.createIntBuffer(vertices.length);
		vertBuf.put(vertices);
		vertBuf.flip();

		hVert = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, hVert);
		glBufferData(GL_ARRAY_BUFFER, vertBuf, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	private void editVBO() {
		vertBuf = BufferUtils.createIntBuffer(vertices.length);
		vertBuf.put(vertices);
		vertBuf.flip();

		glBindBuffer(GL_ARRAY_BUFFER, hVert);
		glBufferSubData(GL_ARRAY_BUFFER, 0, vertBuf);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
}
