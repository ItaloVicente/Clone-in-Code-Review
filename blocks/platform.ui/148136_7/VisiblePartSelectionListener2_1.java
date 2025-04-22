package org.eclipse.ui.internal;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

public class VisiblePartSelectionListener implements ISelectionListener, INullSelectionListener {

	private IWorkbenchPart fLastPart;
	private ISelection fLastSelection;
	private ISelectionListener fListener;
	private IWorkbenchPart fPart;

	public VisiblePartSelectionListener(IWorkbenchPart part, ISelectionListener listener) {
		fPart = part;
		fListener = listener;
		IPartService partService = part.getSite().getService(IPartService.class);
		partService.addPartListener(new IPartListener() {

			@Override
			public void partOpened(IWorkbenchPart part) {
			}

			@Override
			public void partDeactivated(IWorkbenchPart part) {
			}

			@Override
			public void partClosed(IWorkbenchPart part) {
				if (part == fPart) {
					fPart.getSite().getPage().removeSelectionListener(VisiblePartSelectionListener.this);
				}
			}

			@Override
			public void partBroughtToTop(IWorkbenchPart part) {
			}

			@Override
			public void partActivated(IWorkbenchPart part) {
			}
		});
	}

	protected IWorkbenchPart getPart() {
		return fPart;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (part == fLastPart && ((selection != null && selection.equals(fLastSelection))
				|| (fLastSelection != null && fLastSelection.equals(selection))
				|| (selection == null && (fListener instanceof INullSelectionListener)))) {
			return;
		}
		fLastPart = part;
		fLastSelection = selection;
		if (getPart().getSite().getPage().isPartVisible(getPart())) {
			if (selection == null && (fListener instanceof INullSelectionListener)) {
				fListener.selectionChanged(part, selection);
			} else if (selection != null) {
				fListener.selectionChanged(part, selection);
			}
		}
	}
}
