
package org.eclipse.ui.internal.navigator.extensions;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.navigator.ILinkHelper;

public class SkeletonLinkHelper implements ILinkHelper {

	public static final ILinkHelper INSTANCE = new SkeletonLinkHelper();
	
	private SkeletonLinkHelper() {
		
	}

	@Override
	public IStructuredSelection findSelection(IEditorInput anInput) {
		return StructuredSelection.EMPTY;
	}

	@Override
	public void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection) {
		
	}

}
