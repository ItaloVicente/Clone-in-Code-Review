package org.eclipse.ui;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;
import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.internal.PartSelectionListener;

public class SelectionListenerFactory {

	public interface ISelectionModel {

		IWorkbenchPart getPart();

		ISelection getCurrentSelection();

		IWorkbenchPart getCurrentSelectionPart();

		ISelection getLastDeliverdSelection();

		IWorkbenchPart getLastDeliveredSelectionPart();
	}

	public static ISelectionListener createVisibleListener(IWorkbenchPart part, ISelectionListener listener) {
		return new PartSelectionListener(part, listener, true, true, false, Collections.emptyList());
	}

	@SafeVarargs
	public static ISelectionListener createVisibleListener(IWorkbenchPart part, ISelectionListener listener,
			Predicate<ISelectionModel>... predicates) {
		return new PartSelectionListener(part, listener, true, true, false, Arrays.asList(predicates));
	}

	public static ISelectionListener createVisibleSelfMutedListener(IWorkbenchPart part, ISelectionListener listener) {
		return new PartSelectionListener(part, listener, true, true, true, Collections.emptyList());
	}

	@SafeVarargs
	public static ISelectionListener createVisibleSelfMutedListener(IWorkbenchPart part, ISelectionListener listener,
			Predicate<ISelectionModel>... predicates) {
		return new PartSelectionListener(part, listener, true, true, true, Arrays.asList(predicates));
	}

	public static Predicate<ISelectionModel> createSelectionFilterPredicate(Class<? extends ISelection> selectionType,
			int count, Class<?> adapterType) {

		Predicate<ISelectionModel> result = new Predicate<ISelectionModel>() {

			@Override
			public boolean test(ISelectionModel model) {

				if (!(model.getCurrentSelection() != null
						&& selectionType.isAssignableFrom(model.getCurrentSelection().getClass()))) {
					return false;
				}

				if (!(count != -1 && model.getCurrentSelection() instanceof IStructuredSelection
						&& ((IStructuredSelection) model.getCurrentSelection()).size() == count)) {
					return false;
				}

				if (adapterType != null && model.getCurrentSelection() instanceof IStructuredSelection) {
					IStructuredSelection sel = (IStructuredSelection) model.getCurrentSelection();
					for (Object object : sel.toArray()) {
						if (Adapters.adapt(object, adapterType) == null) {
							return false;
						}
					}
				}
				return true;
			}
		};
		return result;
	}
}
