package org.eclipse.ui.tests.views.properties.tabbed.dynamic.model;

public class DynamicTestsShape {

	public static final DynamicTestsShape CIRCLE = new DynamicTestsShape(
			"circle"); //$NON-NLS-1$

	public static final DynamicTestsShape SQUARE = new DynamicTestsShape(
			"square"); //$NON-NLS-1$

	public static final DynamicTestsShape STAR = new DynamicTestsShape("star"); //$NON-NLS-1$

	public static final DynamicTestsShape TRIANGLE = new DynamicTestsShape(
			"triangle"); //$NON-NLS-1$

	public static DynamicTestsShape getShape(String value) {
		if (SQUARE.getShape().equals(value)) {
			return SQUARE;
		} else if (CIRCLE.getShape().equals(value)) {
			return CIRCLE;
		} else if (TRIANGLE.getShape().equals(value)) {
			return TRIANGLE;
		} else if (STAR.getShape().equals(value)) {
			return STAR;
		}
		return null;
	}

	private String shape;

	private DynamicTestsShape(String aShape) {
		setShape(aShape);
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String aShape) {
		this.shape = aShape;
	}

	public String toString() {
		return getShape();
	}

}
