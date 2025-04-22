package org.eclipse.ui.tests.views.properties.tabbed.dynamic.tab.descriptors;

import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.tests.views.properties.tabbed.Activator;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.section.descriptors.DynamicTestsBlackSectionDescriptor;
import org.eclipse.ui.tests.views.properties.tabbed.dynamic.views.DynamicTestsTypeMapper;
import org.eclipse.ui.views.properties.tabbed.AbstractTabDescriptor;

public class DynamicTestsBlackTabDescriptor extends AbstractTabDescriptor {
	private Image image;

	public DynamicTestsBlackTabDescriptor() {
		super();
		getSectionDescriptors().add(
				new DynamicTestsBlackSectionDescriptor(
						new DynamicTestsTypeMapper()));
	}

	public String getAfterTab() {
		return "ColorTab"; //$NON-NLS-1$
	}

	public String getCategory() {
		return "default"; //$NON-NLS-1$
	}

	public String getId() {
		return "BlackTab"; //$NON-NLS-1$
	}

	public Image getImage() {
		if (image == null) {
			image = Activator
					.getImageDescriptor("icons/black_triangle.gif").createImage(); //$NON-NLS-1$ 
		}
		return image;
	}

	public String getLabel() {
		return "Black"; //$NON-NLS-1$
	}

	public boolean isIndented() {
		return true;
	}

}
