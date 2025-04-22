package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractSectionDescriptor;
import org.eclipse.ui.views.properties.tabbed.ISection;

public class OverrideTestsSectionDescriptor extends AbstractSectionDescriptor {

	private ISection section;

	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		return true;
	}

	public String getId() {
		return "org.eclipse.ui.tests.views.properties.tabbed.override"; //$NON-NLS-1$
	}

	public ISection getSectionClass() {
		if (section == null) {
			this.section = new OverrideTestsSection();
		}
		return section;
	}

	public String getTargetTab() {
		return "org.eclipse.ui.tests.views.properties.tabbed.override"; //$NON-NLS-1$
	}
}
