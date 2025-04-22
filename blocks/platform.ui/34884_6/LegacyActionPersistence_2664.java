
package org.eclipse.ui.internal.menus;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchWindow;

public abstract class InternalControlContribution extends ControlContribution {
	private IWorkbenchWindow wbw;
	private int curSide;
	
	protected InternalControlContribution(String id) {
		super(id);
	}

	public InternalControlContribution() {
		this("unknown ID"); //$NON-NLS-1$
	}
	
	public IWorkbenchWindow getWorkbenchWindow() {
		return wbw;
	}

	public void setWorkbenchWindow(IWorkbenchWindow wbw) {
		this.wbw = wbw;
	}

	public int getCurSide() {
		return curSide;
	}

	public void setCurSide(int curSide) {
		this.curSide = curSide;
	}
	
	public int getOrientation() {
		if (getCurSide() == SWT.LEFT || getCurSide() == SWT.RIGHT)
			return SWT.VERTICAL;
		
		return SWT.HORIZONTAL;
	}
}
