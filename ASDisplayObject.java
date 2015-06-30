package ASUtils;

public class ASDisplayObject {
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	protected double anchorX;
	protected double anchorY;

	protected int angle;

	protected double alpha;
	protected double r;
	protected double g;
	protected double b;

	protected int hShader;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;

		if (alpha > 1) {
			this.alpha = 1;
		}

		if (alpha < 0) {
			this.alpha = 0;
		}
	}

	public void setColor(double r, double g, double b) {
		if (r < 0) {
			r = 0;
		}

		if (r > 1) {
			r = 1;
		}

		if (g < 0) {
			g = 0;
		}

		if (g > 1) {
			g = 1;
		}

		if (b < 0) {
			b = 0;
		}

		if (b > 1) {
			b = 1;
		}
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public double getR() {
		return r;
	}

	public double getG() {
		return g;
	}

	public double getB() {
		return b;
	}

	public int getRotation() {
		return angle;
	}

	public int getAdjustedRotation() {
		return ((angle % 360) + 360) % 360;
	}

	public void setRotation(int angle) {
		this.angle = angle;
	}

	public ASDisplayObject() {
		x = 0;
		y = 0;
		anchorX = 0;
		anchorY = 0;
		width = 0;
		height = 0;

		this.alpha = 1;
		this.r = 1;
		this.g = 1;
		this.b = 1;

		this.angle = 0;

		this.hShader = 0;
	}

	public ASDisplayObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.anchorX = 0;
		this.anchorY = 0;
		this.width = width;
		this.height = height;

		this.alpha = 1;
		this.r = 1;
		this.g = 1;
		this.b = 1;

		this.angle = 0;

		this.hShader = 0;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public double getAnchorX() {
		return anchorX;
	}

	public void setAnchorX(double anchorX) {
		this.anchorX = anchorX;
	}

	public double getAnchorY() {
		return anchorY;
	}

	public void setAnchorY(double anchorY) {
		this.anchorY = anchorY;
	}

	public void setAnchors(double anchorX, double anchorY) {
		this.anchorX = anchorX;
		this.anchorY = anchorY;
	}

	public void render() {

	}

	public int getShaderProgram() {
		return hShader;
	}

	public void setShaderProgram(int hShader) {
		this.hShader = hShader;
	}

	public void activate() {
		render();
	}
}
