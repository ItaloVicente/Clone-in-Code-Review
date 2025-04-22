package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsColor;

public class DynamicTestsBlackSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToColor(DynamicTestsColor color) {
		return DynamicTestsColor.BLACK.equals(color);
	}
}
