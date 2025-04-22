package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsShape;

public class DynamicTestsTriangleSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToShape(DynamicTestsShape shape) {
		return DynamicTestsShape.TRIANGLE.equals(shape);
	}
}
