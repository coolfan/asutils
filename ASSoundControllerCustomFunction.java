package ASUtils;

public interface ASSoundControllerCustomFunction {
	public ASSoundController.SoundData update(int timePassed, int timeLimit, ASSoundController.SoundData start,
			ASSoundController.SoundData end);
}
