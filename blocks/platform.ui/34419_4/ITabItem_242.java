package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchPart;

public interface ITabDescriptorProvider {

	public ITabDescriptor[] getTabDescriptors(IWorkbenchPart part,
			ISelection selection);
}
