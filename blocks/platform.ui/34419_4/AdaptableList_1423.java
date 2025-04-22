
package org.eclipse.ui.menus;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.menus.InternalControlContribution;

public abstract class WorkbenchWindowControlContribution extends InternalControlContribution {

	public WorkbenchWindowControlContribution() {
		this("unknown ID"); //$NON-NLS-1$
	}

	public WorkbenchWindowControlContribution(String id) {
		super(id);
	}

	@Override
	public final IWorkbenchWindow getWorkbenchWindow() {
		return super.getWorkbenchWindow();
	}

	@Override
	public final int getCurSide() {
		return super.getCurSide();
	}
	
	@Override
	public final int getOrientation() {
		if (getCurSide() == SWT.LEFT || getCurSide() == SWT.RIGHT)
			return SWT.VERTICAL;
		
		return SWT.HORIZONTAL;
	}

	public Control delegateCreateControl(Composite parent) {
		return createControl(parent);
	}
}
