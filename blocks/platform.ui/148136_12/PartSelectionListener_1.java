package org.eclipse.ui.internal;

import java.util.function.Predicate;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.SelectionListenerFactory;
import org.eclipse.ui.SelectionListenerFactory.ISelectionModel;

public class PartSelectionListener implements ISelectionListener, INullSelectionListener {

	private ISelectionListener fCallbackListener;
	private IWorkbenchPart fCurrentSelectionPart;
	private IWorkbenchPart fLastDeliveredPart;
	private ISelection fCurrentSelection;
	private ISelection fLastDeliveredSelection;
	private IWorkbenchPart fTargetPart;
	private boolean fTargetPartVisible;
	private Predicate<ISelectionModel> fPredicate;

	public PartSelectionListener(IWorkbenchPart part, ISelectionListener callbackListener,
			Predicate<ISelectionModel> predicate) {
		saveArguments(part, callbackListener, predicate);
		addPartListener(part);
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (selection == null && !(part instanceof INullSelectionListener)) {
			return;
		}
		saveCurrentSelection(part, selection);
		if (!fPredicate.test(getModel())) {
			return;
		}
		doCallback(part, selection);
	}

	private void doCallback(IWorkbenchPart part, ISelection selection) {
		if (selection == null && (fCallbackListener instanceof INullSelectionListener)) {
			fCallbackListener.selectionChanged(part, selection);
			saveLastDelivered(part, selection);
		} else if (selection != null) {
			fCallbackListener.selectionChanged(part, selection);
			saveLastDelivered(part, selection);
		}
	}

	private ISelectionModel getModel() {
		return new ISelectionModel() {

			@Override
			public IWorkbenchPart getTargetPart() {
				return fTargetPart;
			}

			@Override
			public IWorkbenchPart getLastDeliveredSelectionPart() {
				return fLastDeliveredPart;
			}

			@Override
			public ISelection getLastDeliverdSelection() {
				return fLastDeliveredSelection;
			}

			@Override
			public IWorkbenchPart getCurrentSelectionPart() {
				return fCurrentSelectionPart;
			}

			@Override
			public ISelection getCurrentSelection() {
				return fCurrentSelection;
			}

			@Override
			public boolean isTargetPartVisible() {
				return fTargetPartVisible;
			}

			@Override
			public boolean isSelectionPartVisible() {
				return getCurrentSelectionPart() != null
						&& getCurrentSelectionPart().getSite().getPage().isPartVisible(getCurrentSelectionPart());
			}
		};
	}

	private void addPartListener(IWorkbenchPart part) {
		IPartService partService = part.getSite().getService(IPartService.class);
		partService.addPartListener(new IPartListener2() {
			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fTargetPart) {
					fTargetPartVisible = true;
					selectionChanged(fCurrentSelectionPart, fCurrentSelection);
				}
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fTargetPart) {
					fTargetPartVisible = false;
				}
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fTargetPart) {
					fTargetPart.getSite().getPage().removeSelectionListener(PartSelectionListener.this);
				}
			}
		});
	}

	private void saveArguments(IWorkbenchPart part, ISelectionListener callbackListener,
			Predicate<ISelectionModel> predicate) {
		fTargetPart = part;
		fCallbackListener = callbackListener;
		decorate(predicate, true);
	}

	private void saveCurrentSelection(IWorkbenchPart part, ISelection selection) {
		fCurrentSelectionPart = part;
		fCurrentSelection = selection;
	}

	private void saveLastDelivered(IWorkbenchPart part, ISelection selection) {
		fLastDeliveredPart = part;
		fLastDeliveredSelection = selection;
	}

	public PartSelectionListener decorate(Predicate<ISelectionModel> predicate, boolean replace) {
		if (replace == true) {
			fPredicate = null;
		}
		return addPredicate(predicate);
	}

	public PartSelectionListener addPredicate(Predicate<ISelectionModel> predicate) {
		if (fPredicate == null) {
			fPredicate = predicate;
		} else {
			fPredicate.and(predicate);
		}
		return this;
	}
}
