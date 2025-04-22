package org.eclipse.ui.internal.navigator.resources.nesting;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class HideFolderWhenProjectIsShownAsNested extends ViewerFilter {

	public static final String EXTENTSION_ID = "org.eclipse.ui.navigator.resources.nesting.HideFolderWhenProjectIsShownAsNested"; //$NON-NLS-1$
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFolder) {
			if (NestedProjectManager.isShownAsProject((IFolder)element)) {
				return false;
			}
		}
		return true;
	}

}
