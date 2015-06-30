package ASUtils;

import java.io.*;

import javax.imageio.*;

import java.awt.image.*;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL31;

import java.util.*;
import java.nio.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class ASTexture {
	protected BufferedImage bImage;

	protected int width;
	protected int height;

	protected int textureHandle;

	private ArrayList<Integer> editCutID;
	private boolean editBufferRequested;

	private boolean makeBufferRequested;

	private ArrayList<double[]> cutList;
	private int cutListBufferHandle;

	private int filter;

	public static final int CUT_SIZE = 8;

	public ASTexture(String path, int filter) {
		try {
			bImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.filter = filter;

		width = bImage.getWidth();
		height = bImage.getHeight();

		cutList = new ArrayList<double[]>();
		cutListBufferHandle = glGenBuffers();

		makeTexture();

		makeBufferRequested = false;
		editBufferRequested = false;
		editCutID = new ArrayList<Integer>();
	}

	public ASTexture(final String path) {
		try {
			bImage = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}

		filter = GL_LINEAR;

		width = bImage.getWidth();
		height = bImage.getHeight();

		cutList = new ArrayList<double[]>();
		cutListBufferHandle = glGenBuffers();

		makeTexture();

		makeBufferRequested = false;
		editBufferRequested = false;
		editCutID = new ArrayList<Integer>();

		// widthCutAdjustment = getCut(bImage.getWidth());
		// heightCutAdjustment = getCut(bImage.getHeight());
	}

	public ASTexture() {
		width = 0;
		height = 0;

		cutList = new ArrayList<double[]>();
		cutListBufferHandle = glGenBuffers();

		// widthCutAdjustment = 0;
		// heightCutAdjustment = 0;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void bind() {
		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL31.GL_TEXTURE_RECTANGLE);
		glBindTexture(GL31.GL_TEXTURE_RECTANGLE, textureHandle);
		glPopAttrib();
	}

	public void delete() {
		glDeleteTextures(textureHandle);
	}

	public void makeBuffer() {
		if (makeBufferRequested) {
			DoubleBuffer cutListBuffer = BufferUtils
					.createDoubleBuffer(CUT_SIZE * cutList.size());
			for (double[] arr : cutList) {
				cutListBuffer.put(arr);
			}

			cutListBuffer.flip();

			glBindBuffer(GL_ARRAY_BUFFER, cutListBufferHandle);
			glBufferData(GL_ARRAY_BUFFER, cutListBuffer, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			makeBufferRequested = false;
			editBufferRequested = false;
			editCutID.clear();
		}

		if (editBufferRequested) {
			for (int ID : editCutID) {
				DoubleBuffer editBuffer = BufferUtils
						.createDoubleBuffer(CUT_SIZE);
				editBuffer.put(cutList.get(ID));
				editBuffer.flip();

				glBindBuffer(GL_ARRAY_BUFFER, cutListBufferHandle);
				glBufferSubData(GL_ARRAY_BUFFER, ID * CUT_SIZE * 8, editBuffer);
				glBindBuffer(GL_ARRAY_BUFFER, 0);
			}

			editBufferRequested = false;
			editCutID.clear();
		}
	}

	public int addTextureCut(int xBegin, int yBegin, int xEnd, int yEnd) {
		double[] temp = new double[CUT_SIZE];
		temp[0] = xBegin;// / widthCutAdjustment;
		temp[1] = yBegin;// / heightCutAdjustment;
		temp[2] = xEnd;// / widthCutAdjustment;
		temp[3] = yBegin;// / heightCutAdjustment;
		temp[4] = xEnd;// / widthCutAdjustment;
		temp[5] = yEnd;// / heightCutAdjustment;
		temp[6] = xBegin;// / widthCutAdjustment;
		temp[7] = yEnd;// / heightCutAdjustment;

		cutList.add(temp);

		makeBufferRequested = true;

		return cutList.size() - 1;
	}

	public int getTextureHandle() {
		return cutListBufferHandle;
	}

	public void editCut(int id, int xBegin, int yBegin, int xEnd, int yEnd) {
		double[] info = cutList.get(id);
		info[0] = xBegin;
		info[1] = yBegin;
		info[2] = xEnd;
		info[3] = yBegin;
		info[4] = xEnd;
		info[5] = yEnd;
		info[6] = xBegin;
		info[7] = yEnd;

		editBufferRequested = true;
		editCutID.add(id);
	}

	/**
	 * 
	 * @param num
	 * @return
	 */

	public static int getCut(int num) {
		int a = 1;
		while (num > a) {
			a *= 2;
		}

		return a;
	}

	private void makeTexture() {
		int[] data = new int[bImage.getWidth() * bImage.getHeight()];
		bImage.getRGB(0, 0, bImage.getWidth(), bImage.getHeight(), data, 0,
				bImage.getWidth());

		ByteBuffer buf = BufferUtils.createByteBuffer(bImage.getWidth()
				* bImage.getHeight() * 4);

		for (int i = 0; i < bImage.getHeight(); i++) {
			for (int j = 0; j < bImage.getWidth(); j++) {
				int pixel = data[i * bImage.getWidth() + j];

				byte[] temp = new byte[4];
				temp[0] = (byte) (pixel >> 16);
				temp[1] = (byte) (pixel >> 8);
				temp[2] = (byte) (pixel >> 0);
				temp[3] = (byte) (pixel >> 24);

				buf.put(temp);
			}
		}

		buf.flip();

		glPushAttrib(GL_ENABLE_BIT);
		glEnable(GL31.GL_TEXTURE_RECTANGLE);

		textureHandle = glGenTextures();
		glBindTexture(GL31.GL_TEXTURE_RECTANGLE, textureHandle);

		glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL_TEXTURE_WRAP_S,
				GL12.GL_CLAMP_TO_EDGE);
		glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL_TEXTURE_WRAP_T,
				GL12.GL_CLAMP_TO_EDGE);

		glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL_TEXTURE_MAG_FILTER,
				filter);
		glTexParameteri(GL31.GL_TEXTURE_RECTANGLE, GL_TEXTURE_MIN_FILTER,
				filter);

		glTexImage2D(GL31.GL_TEXTURE_RECTANGLE, 0, GL_RGBA, bImage.getWidth(),
				bImage.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buf);

		glPopAttrib();
	}
}
