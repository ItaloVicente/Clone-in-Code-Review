package org.eclipse.ui.internal.navigator.resources.nested;

import org.eclipse.core.resources.IFolder;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.ui.navigator.CommonViewer;

public class HideFolderWhenProjectIsShownAsNested extends ViewerFilter {

	public static final String EXTENTSION_ID = "org.eclipse.ui.navigator.resources.nested.HideFolderWhenProjectIsShownAsNested"; //$NON-NLS-1$

	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		CommonViewer commonViewer = (CommonViewer)viewer;
		if (commonViewer.getNavigatorContentService().getActivationService().isNavigatorExtensionActive(NestedProjectsContentProvider.EXTENSION_ID)) {
			if (element instanceof IFolder) {
				if (NestedProjectManager.isShownAsProject((IFolder)element)) {
					return false;
				}
			}
		}
		return true;
	}

}
