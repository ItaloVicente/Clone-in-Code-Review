package org.eclipse.ui;

import static org.eclipse.ui.SelectionListenerFactory.Predicates.alreadyDelivered;
import static org.eclipse.ui.SelectionListenerFactory.Predicates.selectionAlreadyDelivered;
import static org.eclipse.ui.SelectionListenerFactory.Predicates.selectionPartVisible;
import static org.eclipse.ui.SelectionListenerFactory.Predicates.selfMute;

import java.util.Objects;
import java.util.function.Predicate;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.PartSelectionListener;

public class SelectionListenerFactory {

	public interface ISelectionModel {

		IWorkbenchPart getTargetPart();

		ISelection getCurrentSelection();

		IWorkbenchPart getCurrentSelectionPart();

		ISelection getLastDeliverdSelection();

		IWorkbenchPart getLastDeliveredSelectionPart();

		boolean isTargetPartVisible();

		boolean isSelectionPartVisible();

	}

	public static class Predicates {

		public static Predicate<ISelectionModel> selectionType(Class<? extends ISelection> selectionType) {
			return model -> !(model.getCurrentSelection() != null
					&& selectionType.isAssignableFrom(model.getCurrentSelection().getClass()));
		}

		public static Predicate<ISelectionModel> selectionSize(int size) {
			return model -> (model.getCurrentSelection() instanceof IStructuredSelection
					&& ((IStructuredSelection) model.getCurrentSelection()).size() == size);
		}

		public static Predicate<ISelectionModel> minimalSelectionSize(int size) {
			return model -> (model.getCurrentSelection() instanceof IStructuredSelection
					&& ((IStructuredSelection) model.getCurrentSelection()).size() >= size);
		}

		public static Predicate<ISelectionModel> emptySelection = model -> model.getCurrentSelection() != null
				&& !model.getCurrentSelection().isEmpty();

		public static Predicate<ISelectionModel> adaptsTo(Class<?> adapterType) {
			return model -> {
				if (model.getCurrentSelection() instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) model.getCurrentSelection();
					for (Object object : sel.toArray()) {
						if (Adapters.adapt(object, adapterType) == null) {
							return false;
						}
					}
				}
				return true;
			};
		}

		public static Predicate<ISelectionModel> selectionPartVisible = model -> model.isSelectionPartVisible();

		public static Predicate<ISelectionModel> alreadyDelivered = model -> !(Objects
				.equals(model.getCurrentSelectionPart(), model.getLastDeliveredSelectionPart())
				&& Objects.equals(model.getCurrentSelection(), model.getLastDeliverdSelection()));

		public static Predicate<ISelectionModel> selectionAlreadyDelivered = model -> !Objects
				.equals(model.getCurrentSelection(), model.getLastDeliverdSelection());

		public static Predicate<ISelectionModel> selfMute = model -> model.getCurrentSelectionPart() != model
				.getTargetPart();

		public static Predicate<ISelectionModel> targetPartVisible = model -> model.isTargetPartVisible();
	}

	public static ISelectionListener createListener(IWorkbenchPart part, Predicate<ISelectionModel> predicate) {
		return new PartSelectionListener(part, (ISelectionListener) part, predicate);
	}

	public static ISelectionListener createListener(IWorkbenchPart part, ISelectionListener listener,
			Predicate<ISelectionModel> predicate) {
		return new PartSelectionListener(part, listener, predicate);
	}

	public static ISelectionListener createVisibleListener(IWorkbenchPart part, ISelectionListener listener) {
		return new PartSelectionListener(part, listener, alreadyDelivered.and(selectionPartVisible));
	}

	public static ISelectionListener createVisibleListener(IWorkbenchPart part, ISelectionListener listener,
			Predicate<ISelectionModel> predicate) {
		return ((PartSelectionListener) createVisibleListener(part, listener)).addPredicate(predicate);
	}

	public static ISelectionListener createVisibleSelfMutedListener(IWorkbenchPart part, ISelectionListener listener) {
		return new PartSelectionListener(part, listener,
				selectionAlreadyDelivered.and(selectionPartVisible).and(selfMute));
	}

	public static ISelectionListener createVisibleSelfMutedListener(IWorkbenchPart part, ISelectionListener listener,
			Predicate<ISelectionModel> predicate) {
		return ((PartSelectionListener) createVisibleSelfMutedListener(part, listener)).addPredicate(predicate);
	}

	public static ISelectionListener decorate(ISelectionListener listener, Predicate<ISelectionModel> predicate,
			boolean replace) {
		return ((PartSelectionListener) listener).decorate(predicate, replace);
	}
}
