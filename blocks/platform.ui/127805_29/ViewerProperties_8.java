	public static <S extends ISelectionProvider, E> IViewerListProperty<S, E> multiplePostSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(true, null);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multiplePostSelection(
			Class<T> elementType) {
		return new SelectionProviderMultipleSelectionProperty<>(true, elementType);
