package ASUtils;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class ASDisplayMaker {
	/**
	 * 
	 * @param width
	 * @param height
	 * @param title
	 */

	public static void makeDisplay(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(title);
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (Exception e) {
			A.a(e.toString());
			e.printStackTrace();
		}
	}

	public static void makeFullscreenDisplay(DisplayMode mode, String title,
			boolean fscreen) {
		try {
			Display.setFullscreen(fscreen);
			Display.setDisplayMode(mode);
			Display.setTitle(title);
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (Exception e) {
			A.a(e.toString());
			e.printStackTrace();
		}
	}

	public static void setFullscreen(boolean fscreen) {
		try {
			Display.setFullscreen(fscreen);
		} catch (LWJGLException e) {
			A.a(e.toString());
			e.printStackTrace();
		}
	}
}
