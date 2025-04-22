package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsShape;

public class DynamicTestsSquareSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToShape(DynamicTestsShape shape) {
		return DynamicTestsShape.SQUARE.equals(shape);
	}
}
