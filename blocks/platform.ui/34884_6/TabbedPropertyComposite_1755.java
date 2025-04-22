package org.eclipse.ui.internal.views.properties.tabbed.view;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchPart;

public class TabListContentProvider
	implements IStructuredContentProvider {
	
	protected TabbedPropertyRegistry registry;

	protected IWorkbenchPart currentPart;
	
	public TabListContentProvider(TabbedPropertyRegistry registry) {
		this.registry = registry;
	}
	
	public Object[] getElements(Object inputElement) {
		Assert.isTrue(inputElement instanceof ISelection);
			return registry
			.getTabDescriptors(currentPart, (ISelection) inputElement);
	}

	public void dispose() {
	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		this.currentPart = ((TabbedPropertyViewer)viewer).getWorkbenchPart();
	}
}
