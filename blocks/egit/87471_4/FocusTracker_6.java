package org.eclipse.egit.ui.internal.commit;

import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;

public abstract class FocusManagerFormPart implements IFormPart {

	private final FocusTracker focusTracker;

	public FocusManagerFormPart(FocusTracker focusTracker) {
		this.focusTracker = focusTracker;
	}

	@Override
	public void setFocus() {
		Control control = focusTracker.getLastFocusControl();
		if (control != null && control.forceFocus()) {
			return;
		}
		setDefaultFocus();
	}

	public abstract void setDefaultFocus();

	@Override
	public void initialize(IManagedForm form) {
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public void commit(boolean onSave) {
	}

	@Override
	public boolean setFormInput(Object input) {
		return false;
	}

	@Override
	public boolean isStale() {
		return false;
	}

	@Override
	public void refresh() {
	}

}
