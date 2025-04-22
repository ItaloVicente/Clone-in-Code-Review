
package org.eclipse.jface.tests.layout;

import junit.framework.TestCase;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.graphics.Rectangle;

public class GeometryTest extends TestCase {

	public void testNewGeometryMethods() {
		Rectangle margins = Geometry.createDiffRectangle(0, 10, 40, 80);

		Rectangle testRectangle = new Rectangle(100, 100, 100, 100);
		Rectangle expandedRectangle = Geometry.add(testRectangle, margins);
		Rectangle expectedResult = new Rectangle(100, 60, 110, 220);

		assertEquals(expectedResult, expandedRectangle);

		Rectangle difference = Geometry.subtract(expandedRectangle,
				testRectangle);

		assertEquals(margins, difference);

		Geometry.expand(testRectangle, margins);
		assertEquals(expectedResult, testRectangle);
	}

}
