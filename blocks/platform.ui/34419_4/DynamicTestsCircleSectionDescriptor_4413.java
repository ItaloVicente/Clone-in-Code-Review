package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsBlueSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsBlueSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsBlueSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsBlueSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsBlueSectionFilter();
	}

	public String getId() {
		return "DynamicTestsBlueSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsBlueSection();
	}

	public String getTargetTab() {
		return "ColorTab"; //$NON-NLS-1$
	}

}
