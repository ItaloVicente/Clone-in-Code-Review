package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class VisiblePartSelectionListener implements ISelectionListener {

	private IWorkbenchPart fLastPart;
	private ISelection fLastSelection;
	private ISelectionListener fListener;
	private IWorkbenchPart fPart;

	public VisiblePartSelectionListener(IWorkbenchPart part, ISelectionListener listener) {
		fPart = part;
		fListener = listener;
	}

	protected IWorkbenchPart getPart() {
		return fPart;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == fLastPart && ((selection != null && selection.equals(fLastSelection))
				|| (fLastSelection != null && fLastSelection.equals(selection)) || selection == null)) {
			return;
		}
		fLastPart = part;
		fLastSelection = selection;
		if (getPart().getSite().getPage().isPartVisible(getPart())) {
			fListener.selectionChanged(part, selection);
		}
	}
}
