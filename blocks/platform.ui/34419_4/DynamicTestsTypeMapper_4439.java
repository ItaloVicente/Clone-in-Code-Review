
package org.eclipse.ui.tests.views.properties.tabbed.dynamic.views;

import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsElement;
import org.eclipse.ui.views.properties.tabbed.ITabbedPropertySheetPageContributor;

public class DynamicTestsTreeNode extends TreeNode implements
		ITabbedPropertySheetPageContributor {

	public DynamicTestsTreeNode(Object object) {
		super(object);
	}

	public String getContributorId() {
		return getDynamicTestsElement().getContributorId();
	}

	public DynamicTestsElement getDynamicTestsElement() {
		return (DynamicTestsElement) getValue();
	}
}
