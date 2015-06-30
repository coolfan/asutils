package ASUtils;

public interface ASAnimationCustomFunction {
	public ASAnimation.AnimationData update(int timePassed, int timeLimit,
			ASAnimation.AnimationData start, ASAnimation.AnimationData end);
}
