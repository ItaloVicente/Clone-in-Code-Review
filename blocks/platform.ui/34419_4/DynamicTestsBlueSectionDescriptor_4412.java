package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsBlackSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsBlackSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsBlackSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsBlackSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsBlackSectionFilter();
	}

	public String getId() {
		return "DynamicTestsBlackSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsBlackSection();
	}

	public String getTargetTab() {
		return "BlackTab"; //$NON-NLS-1$
	}

}
