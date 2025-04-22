package org.eclipse.ui.internal;

import org.eclipse.swt.widgets.Composite;

import org.eclipse.core.runtime.IStatus;

import org.eclipse.ui.internal.part.StatusPart;
import org.eclipse.ui.part.ViewPart;

public class ErrorViewPart extends ViewPart {

	private IStatus error;
	private Composite parentControl;

	public ErrorViewPart() {
	}

	public ErrorViewPart(IStatus error) {
		this.error = error;
	}

	@Override
	public void createPartControl(Composite parent) {
		parentControl = parent;
		if (error != null) {
			new StatusPart(parent, error);
		}
	}

	@Override
	public void setPartName(String newName) {
		super.setPartName(newName);
	}

	@Override
	public void setFocus() {
		parentControl.setFocus();
	}

	@Override
	public void dispose() {
		super.dispose();
		parentControl = null;
	}

}
