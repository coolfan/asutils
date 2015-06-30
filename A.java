package ASUtils;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.lwjgl.opengl.Display;

public class A {
	public static void a(String errorMessage) {
		ImageIcon error = new ImageIcon("res/error-icon-large.png");
		Display.destroy();
		JOptionPane.showMessageDialog(new JFrame(), errorMessage,
				"Debugger Message", JOptionPane.ERROR_MESSAGE, error);
		System.exit(1);
	}

	public static void a(String errorMessage, String title) {
		ImageIcon error = new ImageIcon("res/error-icon-large.png");
		Display.destroy();
		JOptionPane.showMessageDialog(new JFrame(), errorMessage, title,
				JOptionPane.ERROR_MESSAGE, error);
		System.exit(1);
	}
}
