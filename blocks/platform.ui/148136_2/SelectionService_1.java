package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class InvisiblePartSelectionListener implements ISelectionListener {

	private IWorkbenchPart fLastPart;
	private ISelection fLastSelection;
	private ISelectionListener fListener;
	private IWorkbenchPart fPart;

	public InvisiblePartSelectionListener(IWorkbenchPart part, ISelectionListener listener) {
		fPart = part;
		fListener = listener;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			if (part == fLastPart && ((selection != null && selection.equals(fLastSelection))
					|| (fLastSelection != null && fLastSelection.equals(selection)) || selection == null)) {
				return;
			}
			fLastPart = part;
			fLastSelection = selection;
			if (fPart.getSite().getPage().isPartVisible(fPart)) {
			fListener.selectionChanged(part, selection);
		}
	}
}
