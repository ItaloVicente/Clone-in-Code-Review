package org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsColor;

public class DynamicTestsRedSectionFilter extends
		DynamicTestsElementSectionFilter {

	protected boolean appliesToColor(DynamicTestsColor color) {
		return DynamicTestsColor.RED.equals(color);
	}
}
