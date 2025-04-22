package org.eclipse.ui.tests.api;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

public class MockEditorActionDelegate extends MockActionDelegate implements
        IEditorActionDelegate {
    private IEditorPart target;

    public MockEditorActionDelegate() {
        super();
    }

    @Override
	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
        callHistory.add("setActiveEditor");
        target = targetEditor;
    }

    public IEditorPart getActiveEditor() {
        return target;
    }

}

