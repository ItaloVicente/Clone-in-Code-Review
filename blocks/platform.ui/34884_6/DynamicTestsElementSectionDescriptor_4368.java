package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsCircleSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsCircleSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsCircleSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsCircleSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsCircleSectionFilter();
	}

	public String getId() {
		return "DynamicTestsCircleSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsCircleSection();
	}

	public String getTargetTab() {
		return "ShapeTab"; //$NON-NLS-1$
	}

}
