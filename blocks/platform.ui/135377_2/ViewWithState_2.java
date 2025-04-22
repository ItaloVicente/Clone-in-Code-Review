package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IPersistableEditor;
import org.eclipse.ui.part.EditorPart;

public class EditorWithState extends EditorPart implements IPersistableEditor {

	public static final String ID = "org.eclipse.ui.tests.api.workbenchpart.EditorWithState";

	private static final String STATE = "state";

	public int fState = 0;

	@Override
	public void restoreState(IMemento memento) {
		if (memento != null) {
			Integer i = memento.getInteger(STATE);
			if (i != null) {
				fState = i.intValue();
			}
		}
	}

	@Override
	public void saveState(IMemento memento) {
		memento.putInteger(STATE, fState);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) {
		setSite(site);
		setInput(input);
	}

	@Override
	public void createPartControl(Composite parent) {
	}

	@Override
	public void setFocus() {
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
	public void doSave(IProgressMonitor monitor) {
	}

	@Override
	public void doSaveAs() {
	}

}
