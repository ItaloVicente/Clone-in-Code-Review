package org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.ui.tests.views.properties.tabbed.dynamic.model.DynamicTestsElement;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.AdvancedPropertySection;
import org.eclipse.ui.views.properties.tabbed.ISection;
import org.eclipse.ui.views.properties.tabbed.ITypeMapper;

public class DynamicTestsAdvancedSectionDescriptor extends
		AbstractSectionDescriptor {

	public DynamicTestsAdvancedSectionDescriptor(ITypeMapper typeMapper) {
		super(typeMapper);
	}

	public int getEnablesFor() {
		return 1;
	}

	public String getId() {
		return "AdvancedSection"; //$NON-NLS-1$
	}

	public List getInputTypes() {
		List list = new ArrayList();
		list.add(DynamicTestsElement.class.getName());
		return list;
	}

	public ISection getSectionClass() {
		return new AdvancedPropertySection();
	}

	public String getTargetTab() {
		return "AdvancedTab"; //$NON-NLS-1$
	}

}
