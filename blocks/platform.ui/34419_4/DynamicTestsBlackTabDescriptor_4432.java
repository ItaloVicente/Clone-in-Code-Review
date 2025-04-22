package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsAdvancedSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class DynamicTestsAdvancedTabDescriptor extends AbstractTabDescriptor {

	public DynamicTestsAdvancedTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsAdvancedSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	public String getCategory() {
		return "advanced"; //$NON-NLS-1$
	}

	public String getId() {
		return "AdvancedTab"; //$NON-NLS-1$
	}

	public String getLabel() {
		return "Advanced"; //$NON-NLS-1$
	}

}
