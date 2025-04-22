
package org.eclipse.ui.tests.commands;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class EditorActionDelegate implements IEditorActionDelegate {

	static boolean executed = false;
	static IEditorPart part = null;

	@Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		part = targetEditor;
	}

	@Override
	public void run(IAction action) {
		executed = true;
	}

	@Override
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
