package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsSquareSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsSquareSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsSquareSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsSquareSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsSquareSectionFilter();
	}

	public String getId() {
		return "DynamicTestsSquareSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsSquareSection();
	}

	public String getTargetTab() {
		return "ShapeTab"; //$NON-NLS-1$
	}

}
