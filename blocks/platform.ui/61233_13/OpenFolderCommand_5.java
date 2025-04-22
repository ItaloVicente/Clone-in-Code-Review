package org.eclipse.ui.internal.wizards.datatransfer;

import org.eclipse.jface.wizard.ProgressMonitorPart;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Layout;

public class NonUIThreadProgressMonitorPart extends ProgressMonitorPart {

	public NonUIThreadProgressMonitorPart(Composite parent, Layout layout, boolean createStopButton) {
		super(parent, layout, createStopButton);
	}

	@Override
	public void updateLabel() {
		if (Display.getCurrent() != null) {
			super.updateLabel();
		} else {
			fLabel.getDisplay().asyncExec(() -> {
				if (!fLabel.isDisposed()) {
					super.updateLabel();
				}
			});
		}
	}

	@Override
	public void internalWorked(double work) {
		if (Display.getCurrent() != null) {
			super.internalWorked(work);
		} else {
			fLabel.getDisplay().asyncExec(() -> {
				if (!fLabel.isDisposed()) {
					super.internalWorked(work);
				}
			});
		}
	}
}
