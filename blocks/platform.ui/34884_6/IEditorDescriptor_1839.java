package org.eclipse.ui;

import org.eclipse.jface.action.IAction;

public interface IEditorActionDelegate extends IActionDelegate {
    public void setActiveEditor(IAction action, IEditorPart targetEditor);
}
