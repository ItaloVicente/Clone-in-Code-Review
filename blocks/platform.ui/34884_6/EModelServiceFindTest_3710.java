
package org.eclipse.e4.ui.tests.application;

import javax.inject.Inject;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.di.Persist;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;

public class ClientEditor {

	@Inject
	private MDirtyable dirtyable;

	private boolean saveCalled = false;

	boolean focusCalled = false;

	private boolean throwException = false;

	public void setThrowException(boolean throwException) {
		this.throwException = throwException;
	}

	@Focus
	void delegateFocus() {
		focusCalled = true;
	}

	@Persist
	void doSave() {
		saveCalled = true;
		if (throwException) {
			throw new RuntimeException();
		}

		dirtyable.setDirty(false);
	}

	public boolean wasSaveCalled() {
		return saveCalled;
	}

	public boolean wasFocusCalled() {
		return focusCalled;
	}

}
