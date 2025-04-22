package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsGreenSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsGreenSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsGreenSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsGreenSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsGreenSectionFilter();
	}

	public String getId() {
		return "DynamicTestsGreenSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsGreenSection();
	}

	public String getTargetTab() {
		return "ColorTab"; //$NON-NLS-1$
	}

}
