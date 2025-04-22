	public static <S extends ISelectionProvider> IViewerListProperty<S, Object> multipleSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(false);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multipleSelection(Class<T> elementType) {
		return new SelectionProviderMultipleSelectionProperty<>(false);
