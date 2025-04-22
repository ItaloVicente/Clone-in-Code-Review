package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class VisiblePartSelectionListener2 extends VisiblePartSelectionListener {


	public VisiblePartSelectionListener2(IWorkbenchPart part, ISelectionListener listener) {
		super(part, listener);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == getPart()) {
			return;
		}
		super.selectionChanged(part, selection);
	}
}
