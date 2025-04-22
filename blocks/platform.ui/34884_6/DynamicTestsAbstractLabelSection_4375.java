package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsTriangleSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsTriangleSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsTriangleSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsTriangleSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsTriangleSectionFilter();
	}

	public String getId() {
		return "DynamicTestsTriangleSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsTriangleSection();
	}

	public String getTargetTab() {
		return "ShapeTab"; //$NON-NLS-1$
	}

}
