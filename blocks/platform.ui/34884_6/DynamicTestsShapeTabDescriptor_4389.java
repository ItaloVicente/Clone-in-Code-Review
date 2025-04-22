package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsElementSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class DynamicTestsElementTabDescriptor extends AbstractTabDescriptor {

	public DynamicTestsElementTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsElementSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	public String getCategory() {
		return "default"; //$NON-NLS-1$
	}

	public String getId() {
		return "ElementTab"; //$NON-NLS-1$
	}

	public String getLabel() {
		return "Element"; //$NON-NLS-1$
	}

}
