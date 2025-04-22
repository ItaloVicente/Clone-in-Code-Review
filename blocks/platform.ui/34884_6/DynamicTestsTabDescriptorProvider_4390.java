package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsCircleSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsSquareSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsTriangleSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class DynamicTestsShapeTabDescriptor extends AbstractTabDescriptor {

	public DynamicTestsShapeTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsCircleSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsSquareSectionDescriptor(
						new DynamicTestsTypeMapper()));
		getSectionDescriptors().add(
				new DynamicTestsTriangleSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	public String getAfterTab() {
		return "ColorTab"; //$NON-NLS-1$
	}

	public String getCategory() {
		return "default"; //$NON-NLS-1$
	}

	public String getId() {
		return "ShapeTab"; //$NON-NLS-1$
	}

	public String getLabel() {
		return "Shape"; //$NON-NLS-1$
	}

}
