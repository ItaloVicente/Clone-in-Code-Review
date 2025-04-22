	public static <S extends ISelectionProvider, E> IViewerListProperty<S, E> multipleSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(false, null);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multipleSelection(Class<T> elementType) {
		return new SelectionProviderMultipleSelectionProperty<>(false, elementType);
