package org.eclipse.ui.examples.readmetool;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class EditorActionDelegate implements IEditorActionDelegate {
    private IEditorPart editor;

    public EditorActionDelegate() {
    }

    public void run(IAction action) {
        MessageDialog.openInformation(editor.getSite().getShell(), MessageUtil
                .getString("Readme_Editor"), //$NON-NLS-1$
                MessageUtil.getString("Editor_Action_executed")); //$NON-NLS-1$
    }

    public void selectionChanged(IAction action, ISelection selection) {
    }

    public void setActiveEditor(IAction action, IEditorPart editor) {
        this.editor = editor;
    }
}
