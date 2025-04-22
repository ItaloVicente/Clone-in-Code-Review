
package org.eclipse.jface.databinding.viewers.typed;

import org.eclipse.jface.databinding.viewers.IViewerListProperty;
import org.eclipse.jface.databinding.viewers.IViewerSetProperty;
import org.eclipse.jface.databinding.viewers.IViewerValueProperty;
import org.eclipse.jface.internal.databinding.viewers.SelectionProviderMultipleSelectionProperty;
import org.eclipse.jface.internal.databinding.viewers.SelectionProviderSingleSelectionProperty;
import org.eclipse.jface.internal.databinding.viewers.StructuredViewerFiltersProperty;
import org.eclipse.jface.internal.databinding.viewers.ViewerCheckedElementsProperty;
import org.eclipse.jface.internal.databinding.viewers.ViewerInputProperty;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckable;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class ViewerProperties {
	public static <S extends ICheckable, E> IViewerSetProperty<S, E> checkedElements(Object elementType) {
		return new ViewerCheckedElementsProperty<>(elementType);
	}

	public static <S extends ICheckable, T> IViewerSetProperty<S, T> checkedElements(Class<T> elementType) {
		return new ViewerCheckedElementsProperty<>(elementType);
	}

	public static <S extends StructuredViewer> IViewerSetProperty<S, ViewerFilter> filters() {
		return new StructuredViewerFiltersProperty<>();
	}

	public static <S extends Viewer, E> IViewerValueProperty<S, E> input() {
		return new ViewerInputProperty<>(null);
	}

	public static <S extends Viewer, T> IViewerValueProperty<S, T> input(Class<T> inputType) {
		return new ViewerInputProperty<>(inputType);
	}

	public static <S extends ISelectionProvider, E> IViewerListProperty<S, E> multipleSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(false, null);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multipleSelection(Class<T> elementType) {
		return new SelectionProviderMultipleSelectionProperty<>(false, elementType);
	}


	public static <S extends ISelectionProvider, E> IViewerListProperty<S, E> multiplePostSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(true, null);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multiplePostSelection(
			Class<T> elementType) {
		return new SelectionProviderMultipleSelectionProperty<>(true, elementType);
	}

	public static <S extends ISelectionProvider, E> IViewerValueProperty<S, E> singleSelection() {
		return new SelectionProviderSingleSelectionProperty<>(false, null);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singleSelection(Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(false, elementType);
	}

	public static <S extends ISelectionProvider, E> IViewerValueProperty<S, E> singlePostSelection() {
		return new SelectionProviderSingleSelectionProperty<>(true, null);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singlePostSelection(
			Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(true, elementType);
	}
}
