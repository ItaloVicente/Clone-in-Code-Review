package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.ui.views.properties.tabbed.ISectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISectionDescriptorProvider;

public class OverrideTestsSectionDescriptorProvider implements
		ISectionDescriptorProvider {

	private ISectionDescriptor[] sectionDescriptors;

	public ISectionDescriptor[] getSectionDescriptors() {
		if (sectionDescriptors == null) {
			sectionDescriptors = new ISectionDescriptor[] { new OverrideTestsSectionDescriptor() };
		}
		return sectionDescriptors;
	}
}
