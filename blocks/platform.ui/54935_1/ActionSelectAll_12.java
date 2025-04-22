
package org.eclipse.ui.views.markers.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.ResourceUtil;

public class ActionRevealMarker extends MarkerSelectionProviderAction {

	protected IWorkbenchPart part;

	public ActionRevealMarker(IWorkbenchPart part, ISelectionProvider provider) {
		super(provider, Util.EMPTY_STRING); 
		this.part = part;
	}

	@Override
	public void run() {
		
		IEditorPart editor = part.getSite().getPage().getActiveEditor();
		if (editor == null) {
			return;
		}
		IFile file = ResourceUtil.getFile(editor.getEditorInput());
		if (file != null) {
			IMarker marker = getSelectedMarker();
			if (marker.getResource().equals(file)) {
				try {
					IDE.openEditor(part.getSite().getPage(),
							marker, false);
				} catch (CoreException e) {
				}
			}
		}
	}

	@Override
	public void selectionChanged(IStructuredSelection selection) {
		setEnabled(Util.isSingleConcreteSelection(selection));
	}
}
