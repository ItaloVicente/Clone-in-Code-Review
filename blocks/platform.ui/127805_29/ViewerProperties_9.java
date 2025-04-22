	public static <S extends ISelectionProvider, E> IViewerValueProperty<S, E> singleSelection() {
		return new SelectionProviderSingleSelectionProperty<>(false, null);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singleSelection(Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(false, elementType);
