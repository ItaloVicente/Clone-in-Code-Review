	public static <S extends ISelectionProvider> IViewerValueProperty<S, Object> singleSelection() {
		return new SelectionProviderSingleSelectionProperty<>(false);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singleSelection(Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(false);
