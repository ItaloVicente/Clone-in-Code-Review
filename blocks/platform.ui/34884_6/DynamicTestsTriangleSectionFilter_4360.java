package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsShape;

public class DynamicTestsStarSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToShape(DynamicTestsShape shape) {
		return DynamicTestsShape.STAR.equals(shape);
	}
}
