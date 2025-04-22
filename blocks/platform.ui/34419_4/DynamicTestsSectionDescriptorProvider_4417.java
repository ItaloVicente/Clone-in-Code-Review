package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import org.eclipse.jface.viewers.IFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.filters.DynamicTestsRedSectionFilter;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.sections.DynamicTestsRedSection;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsRedSectionDescriptor extends AbstractSectionDescriptor {

	public DynamicTestsRedSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public IFilter getFilter() {
		return new DynamicTestsRedSectionFilter();
	}

	public String getId() {
		return "DynamicTestsRedSection"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		return new DynamicTestsRedSection();
	}

	public String getTargetTab() {
		return "ColorTab"; //$NON-NLS-1$
	}

}
