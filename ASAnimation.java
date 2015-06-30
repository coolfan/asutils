package ASUtils;

import static java.lang.Math.*;
import ASUtils.ASAnimation.AnimationData;

public class ASAnimation {

	public ASAnimation() {

	}

	public ASAnimation(ASDisplayObject toAnimate, int timeLimit, int function) {
		this.toAnimate = toAnimate;
		this.timeLimit = timeLimit;

		start = new AnimationData();
		start.extractInfo(toAnimate);

		end = new AnimationData();
		end.extractInfo(toAnimate);

		this.func = function;

		timer = new ASTimer();
		timerInitialized = false;
		time = 0;

		alphaAnimation = false;
		colorAnimation = false;
		translationAnimation = false;
		rotationAnimation = false;
		dilationAnimation = false;
		anchorAnimation = false;

		isDone = false;

		custom = null;
	}

	public void setTargetColor(double r, double g, double b) {
		end.r = r;
		end.g = g;
		end.b = b;
		colorAnimation = true;
	}

	public void setTargetAlpha(double alpha) {
		end.alpha = alpha;
		alphaAnimation = true;
	}

	public void setTargetLocation(int x, int y) {
		end.x = x;
		end.y = y;
		translationAnimation = true;
	}

	public void setTargetSize(int width, int height) {
		end.width = width;
		end.height = height;
		dilationAnimation = true;
	}

	public void setTargetRotation(int angle) {
		end.angle = angle;
		rotationAnimation = true;
	}

	public void setTargetAnchors(double anchorX, double anchorY) {
		end.anchorX = anchorX;
		end.anchorY = anchorY;
		anchorAnimation = true;
	}

	public void setFunction(int function) {
		this.func = function;
	}

	public void setCustomFunction(ASAnimationCustomFunction a) {
		custom = a;
	}

	public ASAnimationCustomFunction getCustomFunction() {
		return custom;
	}

	public void setObjectToAnimate(ASDisplayObject o) {
		if (!timerInitialized) {
			this.toAnimate = o;

			start.extractInfo(o);
		}
	}

	public void calculateNext() {
		if (!isDone) {
			if (!timerInitialized) {
				timerInitialized = true;
				timer.initTime();
			}

			int delta = timer.getDelta();
			time += delta;

			if (time >= timeLimit) {
				end.setInfo(toAnimate);
				isDone = true;

				return;
			}

			if ((func & CUSTOM_FUNCTION) == 0) {

				if (alphaAnimation) {
					double scaler = 0;

					if ((func & A_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & A_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & A_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & A_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					double newA = scaler * (end.alpha - start.alpha)
							+ start.alpha;

					toAnimate.setAlpha(newA);
				}

				if (colorAnimation) {
					double scaler = 0;

					if ((func & C_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & C_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & C_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & C_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					double newR = scaler * (end.r - start.r) + start.r;
					double newG = scaler * (end.g - start.g) + start.g;
					double newB = scaler * (end.b - start.b) + start.b;

					toAnimate.setColor(newR, newG, newB);

					// System.out.println(newA);
				}

				if (translationAnimation) {
					double scaler = 0;

					if ((func & T_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & T_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & T_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & T_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					int newX = (int) (scaler * (end.x - start.x) + start.x);
					int newY = (int) (scaler * (end.y - start.y) + start.y);

					toAnimate.setLocation(newX, newY);

				}

				if (rotationAnimation) {
					double scaler = 0;

					if ((func & R_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & R_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & R_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & R_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					int newRotation = (int) (scaler * (end.angle - start.angle) + start.angle);

					toAnimate.setRotation(newRotation);
				}

				if (dilationAnimation) {
					double scaler = 0;

					if ((func & D_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & D_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & D_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & D_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					int newWidth = (int) (scaler * (end.width - start.width) + start.width);
					int newHeight = (int) (scaler * (end.height - start.height) + start.height);

					toAnimate.setSize(newWidth, newHeight);
				}

				if (anchorAnimation) {
					double scaler = 0;

					if ((func & A2_SINE) != 0) {
						double arg = (double) time / timeLimit * 90;
						scaler = sin(toRadians(arg));
					} else if ((func & A2_EXPONENTIAL) != 0) {
						double arg = (double) time / timeLimit;
						scaler = (exp(arg) - 1) / (E - 1);
					} else if ((func & A2_LOGARITHMIC) != 0) {
						double arg = ((double) time / timeLimit) * (E - 1) + 1;
						scaler = log(arg);
					} else if ((func & A2_LINEAR) != 0) {
						scaler = (double) time / timeLimit;
					}

					double newAnchorX = (scaler * (end.anchorX - start.anchorX) + start.anchorX);
					double newAnchorY = (scaler * (end.anchorY - start.anchorY) + start.anchorY);

					toAnimate.setAnchors(newAnchorX, newAnchorY);
				}
			} else {
				AnimationData updated = custom.update(time, timeLimit, start,
						end);

				updated.setInfo(toAnimate);
			}
		}
	}

	public boolean isDone() {
		return isDone;
	}

	public void reset() {
		timer = new ASTimer();
		time = 0;
		timerInitialized = false;
		isDone = false;

		start.setInfo(toAnimate);
	}

	protected ASDisplayObject toAnimate;
	protected AnimationData start;
	protected AnimationData end;
	protected int time;
	protected int timeLimit;
	protected ASTimer timer;
	protected boolean timerInitialized;
	protected boolean isDone;

	protected boolean alphaAnimation;
	protected boolean colorAnimation;
	protected boolean translationAnimation;
	protected boolean rotationAnimation;
	protected boolean dilationAnimation;
	protected boolean anchorAnimation;

	protected int func;

	protected ASAnimationCustomFunction custom;

	public static final int NONE = 0;

	public static final int C_SINE = 1 << 0;
	public static final int C_EXPONENTIAL = 1 << 1;
	public static final int C_LOGARITHMIC = 1 << 2;
	public static final int C_LINEAR = 1 << 3;

	public static final int T_SINE = 1 << 5;
	public static final int T_EXPONENTIAL = 1 << 6;
	public static final int T_LOGARITHMIC = 1 << 7;
	public static final int T_LINEAR = 1 << 8;

	public static final int R_SINE = 1 << 10;
	public static final int R_EXPONENTIAL = 1 << 11;
	public static final int R_LOGARITHMIC = 1 << 12;
	public static final int R_LINEAR = 1 << 13;

	public static final int D_SINE = 1 << 15;
	public static final int D_EXPONENTIAL = 1 << 16;
	public static final int D_LOGARITHMIC = 1 << 17;
	public static final int D_LINEAR = 1 << 18;

	public static final int A_SINE = 1 << 20;
	public static final int A_EXPONENTIAL = 1 << 21;
	public static final int A_LOGARITHMIC = 1 << 22;
	public static final int A_LINEAR = 1 << 23;

	public static final int A2_SINE = 1 << 25;
	public static final int A2_EXPONENTIAL = 1 << 26;
	public static final int A2_LOGARITHMIC = 1 << 27;
	public static final int A2_LINEAR = 1 << 28;

	public static final int CUSTOM_FUNCTION = 1 << 30;

	public static class AnimationData {
		public double r, g, b, alpha, anchorX, anchorY;
		public int width, height, x, y, angle;

		public void extractInfo(ASDisplayObject o) {
			r = o.getR();
			g = o.getG();
			b = o.getB();

			alpha = o.getAlpha();

			anchorX = o.getAnchorX();
			anchorY = o.getAnchorY();

			width = o.getWidth();
			height = o.getHeight();
			x = o.getX();
			y = o.getY();

			angle = o.getRotation();
		}

		public void setInfo(ASDisplayObject o) {
			o.setAlpha(alpha);
			o.setLocation(x, y);
			o.setSize(width, height);
			o.setColor(r, g, b);
			o.setRotation(angle);
			o.setAnchors(anchorX, anchorY);
		}

		public void extractInfo(AnimationData ad) {
			ASDisplayObject temp = new ASDisplayObject();
			ad.setInfo(temp);
			
			extractInfo(temp);
		}
	}
}
