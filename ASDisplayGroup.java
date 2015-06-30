package ASUtils;

import java.util.*;
import static org.lwjgl.opengl.GL11.*;

public class ASDisplayGroup extends ASDisplayObject implements ASManageable {

	public ASDisplayGroup(int x, int y) {
		super(x, y, 0, 0);

		displayObjects = new TreeSet<>((a, b) -> {
			return (Integer) (a[0]) - (Integer) (b[0]);
		});

		usedZIndex = new TreeSet<>();

		top = 0;
		bottom = -1;
	}

	public ASDisplayGroup() {
		this(0, 0);
	}

	public void addObjectToTop(ASDisplayObject o) {

		if (usedZIndex.contains(top + 1)) {
			displayObjects.add(new Object[] { top + 2, o });
			top += 2;
		} else {
			displayObjects.add(new Object[] { top + 1, o });
			top++;
		}

		usedZIndex.add(top);
	}

	public void addObjectToBottom(ASDisplayObject o) {
		if (usedZIndex.contains(bottom - 1)) {
			displayObjects.add(new Object[] { bottom - 2, o });
			bottom -= 2;
		} else {
			displayObjects.add(new Object[] { bottom - 1, o });
			bottom--;
		}

		usedZIndex.add(bottom);
	}

	public void removeObject(ASDisplayObject o) {
		for (Object[] temp : displayObjects) {
			ASDisplayObject x = (ASDisplayObject) (temp[1]);

			if (x == o) {
				displayObjects.remove(temp);
				return;
			}
		}
	}

	public void moveToTop(ASDisplayObject o) {
		for (Object[] temp : displayObjects) {
			ASDisplayObject x = (ASDisplayObject) (temp[1]);

			if (x == o) {
				displayObjects.remove(temp);
				break;
			}
		}

		addObjectToTop(o);
	}

	public void moveToBottom(ASDisplayObject o) {
		for (Object[] temp : displayObjects) {
			ASDisplayObject x = (ASDisplayObject) (temp[1]);

			if (x == o) {
				displayObjects.remove(temp);
				break;
			}
		}

		addObjectToBottom(o);
	}

	public void addObject(ASDisplayObject o, int zIndex) {
		if (!usedZIndex.contains(zIndex)) {
			displayObjects.add(new Object[] { new Integer(zIndex), o });
			usedZIndex.add(zIndex);
		}
	}

	public void setObjectZIndex(ASDisplayObject o, int zIndex) {

		if (!usedZIndex.contains(zIndex)) {
			for (Object[] temp : displayObjects) {
				ASDisplayObject x = (ASDisplayObject) (temp[1]);

				if (x == o) {
					displayObjects.remove(temp);
					break;
				}
			}

			addObject(o, zIndex);
		}
	}

	public void removeAllObjects() {
		displayObjects.clear();
	}
	
	@Override
	public void manage() {
		render();
	}

	@Override
	public void render() {
		super.render();

		glPushMatrix();
		glTranslated(x, y, 0);
		glRotated(angle, 0, 0, 1);

		for (Object[] arr : displayObjects) {

			ASDisplayObject o = (ASDisplayObject) (arr[1]);

			if (o instanceof ASDisplayObject) {
				ASDisplayObject temp = (ASDisplayObject) o;
				double oldAlpha = temp.getAlpha();
				double oldR = temp.getR();
				double oldG = temp.getG();
				double oldB = temp.getB();

				temp.setAlpha(oldAlpha * alpha);
				temp.setColor(oldR * r, oldG * g, oldB * b);

				temp.activate();
				temp.setAlpha(oldAlpha);
				temp.setColor(oldR, oldG, oldB);
			} else {
				o.activate();
			}
		}

		glPopMatrix();
	}

	@Override
	public void setShaderProgram(int hShader) {
		super.setShaderProgram(hShader);

		for (Object[] arr : displayObjects) {
			if (arr[1] instanceof ASDisplayObject) {
				((ASDisplayObject) (arr[1])).setShaderProgram(hShader);
			}
		}
	}

	private Set<Object[]> displayObjects;
	private Set<Integer> usedZIndex;
	private int top, bottom;
	
}
