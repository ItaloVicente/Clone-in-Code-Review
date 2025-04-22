package org.eclipse.ui.forms.examples.internal.rcp;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.examples.internal.ExamplesPlugin;
import org.eclipse.ui.forms.widgets.FormToolkit;
public class SimpleFormEditor extends FormEditor {
	public SimpleFormEditor() {
	}
	protected FormToolkit createToolkit(Display display) {
		return new FormToolkit(ExamplesPlugin.getDefault().getFormColors(
				display));
	}
	protected void addPages() {
		try {
		addPage(new NewStylePage(this));
		addPage(new ErrorMessagesPage(this));
		addPage(new FreeFormPage(this));
		addPage(new SecondPage(this));
		int index = addPage(new Composite(getContainer(), SWT.NULL));
		setPageText(index, "Composite");
		addPage(new ThirdPage(this));
		addPage(new ScrolledPropertiesPage(this));
		addPage(new PageWithSubPages(this));
		}
		catch (PartInitException e) {
		}
	}
	public void doSave(IProgressMonitor monitor) {
	}
	public void doSaveAs() {
	}
	public boolean isSaveAsAllowed() {
		return false;
	}
}
