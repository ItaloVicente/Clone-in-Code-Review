	public static <S extends ISelectionProvider, E> IViewerValueProperty<S, E> singlePostSelection() {
		return new SelectionProviderSingleSelectionProperty<>(true, null);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singlePostSelection(
			Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(true, elementType);
