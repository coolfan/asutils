package ASUtils;

public class ASTexturePiece extends ASTexture {

	public ASTexturePiece(ASTexture mainTex, int xBegin, int yBegin, int xEnd,
			int yEnd) {
		this.mainTex = mainTex;

		this.xBegin = xBegin;
		this.yBegin = yBegin;

		this.width = xEnd - xBegin;
		this.height = yEnd - yBegin;
	}

	public ASTexturePiece(int xBegin, int yBegin, int width, int height,
			ASTexture mainTex) {
		this.mainTex = mainTex;

		this.xBegin = xBegin;
		this.yBegin = yBegin;

		this.width = width;
		this.height = height;
	}

	@Override
	public int addTextureCut(int xBegin, int yBegin, int xEnd, int yEnd) {
		if (xEnd > width || yEnd > height) {
			return -1;
		}
		return mainTex.addTextureCut(xBegin + this.xBegin,
				yBegin + this.yBegin, xEnd + this.xBegin, yEnd + this.yBegin);
	}

	@Override
	public int getTextureHandle() {
		return mainTex.getTextureHandle();
	}

	@Override
	public void bind() {
		mainTex.bind();
	}

	@Override
	public void delete() {
		mainTex.delete();
	}

	@Override
	public void makeBuffer() {
		mainTex.makeBuffer();
	}

	private ASTexture mainTex;
	private int xBegin;
	private int yBegin;
}
