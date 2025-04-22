package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.INullSelectionListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartListener2Adapter;
import org.eclipse.ui.SelectionListenerFactory;
import org.eclipse.ui.SelectionListenerFactory.ISelectionModel;

public class PartSelectionListener implements ISelectionListener, INullSelectionListener {

	private ISelectionListener fCallbackListener;
	private IWorkbenchPart fCurrentSelectionPart;
	private IWorkbenchPart fLastDeliveredPart;
	private ISelection fCurrentSelection;
	private ISelection fLastDeliveredSelection;
	private IWorkbenchPart fPart;
	private List<Predicate<ISelectionModel>> fPredicates;
	private boolean fWhenChanged;
	private boolean fVisible;

	public PartSelectionListener(IWorkbenchPart part, ISelectionListener callbackListener, boolean whenVisible,
			boolean whenChanged, boolean selfMute, List<Predicate<ISelectionModel>> predicates) {
		saveArguments(part, callbackListener, whenChanged, predicates);
		addSelectionAlreadyDeliveredPredicate();
		addOriginatingViewMustBeVisiblePredicate();
		addSelfMutePredicate(selfMute);
		addWhenVisiblePredicate(whenVisible);
		addPartListener(part);
	}

	public PartSelectionListener addPredicate(Predicate<ISelectionModel> predicate) {
		if (!fPredicates.contains(predicate)) {
			fPredicates.add(predicate);
		}
		return this;
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		saveLastSelection(part, selection);
		if (isBlockedByPredicates()) {
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


	private boolean isBlockedByPredicates() {
		for (Predicate<ISelectionModel> predicate : fPredicates) {
			if (!predicate.test(getModel())) {
				return true;
			}
		}
		return false;
	}

	private ISelectionModel getModel() {
		return new ISelectionModel() {

			@Override
			public IWorkbenchPart getPart() {
				return fPart;
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
		};
	}

	private void addPartListener(IWorkbenchPart part) {
		IPartService partService = part.getSite().getService(IPartService.class);
		partService.addPartListener(new PartListener2Adapter() {
			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fPart) {
					fVisible = true;
					selectionChanged(fCurrentSelectionPart, fCurrentSelection);
				}
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fPart) {
					fVisible = false;
				}
			}

			@Override
			public void partClosed(IWorkbenchPartReference partRef) {
				if (partRef.getPart(false) == fPart) {
					fPart.getSite().getPage().removeSelectionListener(PartSelectionListener.this);
				}
			}
		});
	}

	private void addOriginatingViewMustBeVisiblePredicate() {
		fPredicates.add(model -> model.getCurrentSelectionPart() != null
				&& model.getCurrentSelectionPart().getSite().getPage().isPartVisible(model.getCurrentSelectionPart()));
	}

	private void addSelectionAlreadyDeliveredPredicate() {
		fPredicates.add(model -> !(fWhenChanged && Objects.equals(model.getCurrentSelectionPart(), fLastDeliveredPart)
				&& Objects.equals(model.getCurrentSelection(), fLastDeliveredSelection)));
	}

	private void addSelfMutePredicate(boolean selfMute) {
		if (selfMute) {
			fPredicates.add(model -> model.getCurrentSelectionPart() != fPart);
		}
	}

	private void addWhenVisiblePredicate(boolean whenVisible) {
		if (whenVisible) {
			fPredicates.add(model -> fVisible);
		}
	}

	private void saveArguments(IWorkbenchPart part, ISelectionListener callbackListener, boolean whenChanged,
			List<Predicate<ISelectionModel>> predicates) {
		fPart = part;
		fCallbackListener = callbackListener;
		fWhenChanged = whenChanged;
		fPredicates = new ArrayList<>();
		fPredicates.addAll(predicates);
	}

	private void saveLastSelection(IWorkbenchPart part, ISelection selection) {
		fCurrentSelectionPart = part;
		fCurrentSelection = selection;
	}

	private void saveLastDelivered(IWorkbenchPart part, ISelection selection) {
		fLastDeliveredPart = part;
		fLastDeliveredSelection = selection;
	}
}
