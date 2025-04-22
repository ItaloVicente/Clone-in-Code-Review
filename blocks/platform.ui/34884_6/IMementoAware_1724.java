package org.eclipse.ui.navigator;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;

public interface ILinkHelper {

	IStructuredSelection findSelection(IEditorInput anInput);

	void activateEditor(IWorkbenchPage aPage, IStructuredSelection aSelection);
}
