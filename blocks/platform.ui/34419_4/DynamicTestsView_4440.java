package org.eclipse.ui.tests.views.properties.tabbed.dynamic.views;

import org.eclipse.ui.views.properties.tabbed.AbstractTypeMapper;

public class DynamicTestsTypeMapper extends AbstractTypeMapper {

	public Class mapType(Object object) {
		if (object instanceof DynamicTestsTreeNode) {
			return ((DynamicTestsTreeNode) object).getDynamicTestsElement()
					.getClass();
		}
		return super.mapType(object);
	}

}
