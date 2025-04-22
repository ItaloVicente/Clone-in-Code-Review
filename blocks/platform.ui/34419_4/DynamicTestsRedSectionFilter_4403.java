package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsColor;

public class DynamicTestsGreenSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToColor(DynamicTestsColor color) {
		return DynamicTestsColor.GREEN.equals(color);
	}
}
