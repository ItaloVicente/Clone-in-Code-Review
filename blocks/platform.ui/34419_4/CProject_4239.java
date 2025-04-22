package org.eclipse.ui.tests.navigator.cdt;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.IWorkbenchAdapter;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

public class CNavigatorLabelProvider extends LabelProvider implements
		ICommonLabelProvider {

	public CNavigatorLabelProvider() {
	}

	@Override
	public void init(ICommonContentExtensionSite extensionSite) {
	}

	@Override
	public void restoreState(IMemento memento) {
	}

	@Override
	public void saveState(IMemento memento) {
	}

	@Override
	public String getText(Object element) {
		return getDescription(element);
	}

	@Override
	public String getDescription(Object element) {
		String desc = "<notfound>";
		if (element instanceof IResource) {
			desc = ((IResource) element).getFullPath().makeRelative()
					.toString();
		} else if (element instanceof CElement) {
			CElement celement = (CElement) element;
			desc = celement.getElementName();
		} else if (element instanceof IWorkbenchAdapter) {
			IWorkbenchAdapter wAdapter = (IWorkbenchAdapter) element;
			desc = wAdapter.getLabel(element);
		}
		return "CL: " + desc;
	}

}
