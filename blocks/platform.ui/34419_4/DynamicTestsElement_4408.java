package org.eclipse.ui.tests.views.properties.tabbed.dynamic.model;

public class DynamicTestsColor {

	public static final DynamicTestsColor BLACK = new DynamicTestsColor("black"); //$NON-NLS-1$

	public static final DynamicTestsColor BLUE = new DynamicTestsColor("blue"); //$NON-NLS-1$

	public static final DynamicTestsColor GREEN = new DynamicTestsColor("green"); //$NON-NLS-1$

	public static final DynamicTestsColor RED = new DynamicTestsColor("red"); //$NON-NLS-1$

	public static DynamicTestsColor getColor(String value) {
		if (RED.getColor().equals(value)) {
			return RED;
		} else if (GREEN.getColor().equals(value)) {
			return GREEN;
		} else if (BLUE.getColor().equals(value)) {
			return BLUE;
		} else if (BLACK.getColor().equals(value)) {
			return BLACK;
		}
		return null;
	}

	private String color;

	private DynamicTestsColor(String aColor) {
		setColor(aColor);
	}

	public String getColor() {
		return color;
	}

	public void setColor(String aColor) {
		this.color = aColor;
	}

	public String toString() {
		return getColor();
	}
}
