package org.eclipse.ui.internal.navigator.resources.workbench;

import org.eclipse.core.resources.IResource;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

public class ResourceExtensionLabelProvider extends WorkbenchLabelProvider implements ICommonLabelProvider {
 
 
	@Override
	public void init(ICommonContentExtensionSite aConfig) {
	}

 
	@Override
	public String getDescription(Object anElement) {

		if (anElement instanceof IResource) {
			return ((IResource) anElement).getFullPath().makeRelative().toString();
		}
		return null;
	}

	@Override
	public void restoreState(IMemento aMemento) { 
		
	}

	@Override
	public void saveState(IMemento aMemento) { 
	}
}
