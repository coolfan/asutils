package ASUtils;

import java.util.Map;

public class ASFadingSprite extends ASSprite {

	private ASSprite[] sprites;
	private ASDisplayGroup group;
	private int curSprite;
	private int transitionTime;
	private ASAnimationList animation;
	private ASManager manager;

	public ASFadingSprite(int x, int y, int width, int height, ASTexture tex,
			int frameWidth, int frameHeight) {
		super(x, y, width, height, tex, frameWidth, frameHeight);

		sprites = new ASSprite[2];
		sprites[0] = new ASSprite(0, 0, width, height, tex, frameWidth,
				frameHeight);
		sprites[1] = new ASSprite(0, 0, width, height, tex, frameWidth,
				frameHeight);

		group = new ASDisplayGroup();
		animation = new ASAnimationList();
		manager = new ASManager();

		group.addObjectToTop(sprites[0]);
		group.addObjectToTop(sprites[1]);
		
		manager.addHighPriority(group);
		manager.addLowPriority(animation);

		curSprite = 0;
		transitionTime = 1000;

		sprites[1].setAlpha(0);
	}

	@Override
	public void render() {
		manager.manage();
	}

	@Override
	public void gotoNextFrame() {
		super.gotoNextFrame();

		int other = (curSprite + 1) % 2;
		sprites[other].setCurrentFrame(sprites[curFrame].getCurrentFrame() + 1);

		addFading();
		curSprite = other;
	}

	@Override
	public void gotoPrevFrame() {
		super.gotoPrevFrame();

		int other = (curSprite + 1) % 2;
		sprites[other].setCurrentFrame(sprites[curFrame].getCurrentFrame() - 1);

		addFading();
		curSprite = other;
	}

	@Override
	public void setCurrentFrame(int curFrame) {
		super.setCurrentFrame(curFrame);

		int other = (curSprite + 1) % 2;
		sprites[other].setCurrentFrame(curFrame);

		addFading();
		curSprite = other;
	}

	@Override
	public void setCurrentFrame(String curFrameName) {
		super.setCurrentFrame(curFrameName);

		int other = (curSprite + 1) % 2;
		sprites[other].setCurrentFrame(curFrameName);

		addFading();
		curSprite = other;
	}

	@Override
	public void setFrameNames(Map<String, Integer> frameNames) {
		super.setFrameNames(frameNames);

		sprites[0].setFrameNames(frameNames);
		sprites[1].setFrameNames(frameNames);
	}

	@Override
	public void setFrameSize(int frameWidth, int frameHeight) {
		super.setFrameSize(frameWidth, frameHeight);

		sprites[0].setFrameSize(frameWidth, frameHeight);
		sprites[1].setFrameSize(frameWidth, frameHeight);
	}

	@Override
	public void setWidth(int width) {
		super.setWidth(width);

		sprites[0].setWidth(width);
		sprites[1].setWidth(width);
	}

	@Override
	public void setHeight(int height) {
		super.setHeight(height);

		sprites[0].setHeight(height);
		sprites[1].setHeight(height);

	}

	@Override
	public void setX(int x) {
		super.setX(x);

		group.setX(x);
	}

	@Override
	public void setY(int y) {
		super.setY(y);

		group.setY(y);
	}

	@Override
	public void setLocation(int x, int y) {
		super.setLocation(x, y);

		group.setLocation(x, y);
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);

		sprites[0].setSize(width, height);
		sprites[1].setSize(width, height);
	}

	@Override
	public void setAnchorX(double anchorX) {
		super.setAnchorX(anchorX);

		sprites[0].setAnchorX(anchorX);
		sprites[1].setAnchorX(anchorX);
	}

	@Override
	public void setAnchorY(double anchorY) {
		super.setAnchorY(anchorY);

		sprites[0].setAnchorY(anchorY);
		sprites[1].setAnchorY(anchorY);
	}

	@Override
	public void setAnchors(double anchorX, double anchorY) {
		super.setAnchors(anchorX, anchorY);

		sprites[0].setAnchors(anchorX, anchorY);
		sprites[1].setAnchors(anchorX, anchorY);
	}

	@Override
	public void setColor(double r, double g, double b) {
		super.setColor(r, g, b);

		group.setColor(r, g, b);
	}

	@Override
	public void setAlpha(double alpha) {
		super.setAlpha(alpha);

		group.setAlpha(alpha);
	}
	
	public ASDisplayObject getObjectToAnimate() {
		return group;
	}

	public void setTransitionTime(int transTime) {
		transitionTime = transTime;
	}

	private void addFading() {
		int other = (curSprite + 1) % 2;

		ASAnimation fade = new ASAnimation(sprites[curSprite], transitionTime,
				ASAnimation.A_SINE);
		fade.setTargetAlpha(0);

		ASAnimation appear = new ASAnimation(sprites[other], transitionTime,
				ASAnimation.A_SINE);
		appear.setTargetAlpha(1);
		
		animation.addAnimation("fade", fade);
		animation.addAnimation("appear", appear);
	}

}
