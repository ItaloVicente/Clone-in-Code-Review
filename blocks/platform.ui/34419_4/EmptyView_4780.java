package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;

public class EditorWithInputException extends EditorPart {

    @Override
	public void doSave(IProgressMonitor monitor) {

    }

    @Override
	public void doSaveAs() {

    }

    @Override
	public void init(IEditorSite site, IEditorInput input)
            throws PartInitException {
        if (!(input instanceof IFileEditorInput)) {
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		}
        setSite(site);
        setInput(input);
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
	public IEditorInput getEditorInput() {
        IllegalStateException exception = new IllegalStateException("This exception was thrown intentionally as part of an error handling test");
        throw exception;
    }

    @Override
	public void createPartControl(Composite parent) {
        Label testLabel = new Label(parent, SWT.NONE);

        testLabel.setText("This throws an exception in getEditorInput");
    }

    @Override
	public void setFocus() {

    }

}
