package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsBlueSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsGreenSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsRedSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class DynamicTestsColorTabDescriptor extends AbstractTabDescriptor {

	public DynamicTestsColorTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsRedSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsGreenSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsBlueSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	public String getAfterTab() {
		return "ElementTab"; //$NON-NLS-1$
	}

	public String getCategory() {
		return "default"; //$NON-NLS-1$
	}

	public String getId() {
		return "ColorTab"; //$NON-NLS-1$
	}

	public String getLabel() {
		return "Color"; //$NON-NLS-1$
	}

}
