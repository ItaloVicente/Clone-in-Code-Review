package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsStarSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsStarSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsStarSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsStarSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsStarSectionFilter();
	}

	public String getId() {
		return "DynamicTestsStarSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsStarSection();
	}

	public String getTargetTab() {
		return "StarTab"; //$NON-NLS-1$
	}

}
