package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class EditorWithInitException extends EditorPart {

    @Override
	public void doSave(IProgressMonitor monitor) {

    }

    @Override
	public void doSaveAs() {

    }

    @Override
	public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        
        throw new PartInitException("This exception was thrown intentionally as part of an error handling test");
    }

    @Override
	public boolean isDirty() {
        return false;
    }

    @Override
	public boolean isSaveAsAllowed() {
        return false;
    }

    @Override
	public void createPartControl(Composite parent) {

        parent.setLayout(new FillLayout());
        
        Label message = new Label(parent, SWT.NONE);
        message.setText("This editor threw an exception on init. You should not be able to read this");
    }

    @Override
	public void setFocus() {

    }

}
