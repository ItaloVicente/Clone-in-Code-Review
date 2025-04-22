	public static <S extends ISelectionProvider> IViewerValueProperty<S, Object> singlePostSelection() {
		return new SelectionProviderSingleSelectionProperty<>(true);
	}

	public static <S extends ISelectionProvider, T> IViewerValueProperty<S, T> singlePostSelection(
			Class<T> elementType) {
		return new SelectionProviderSingleSelectionProperty<>(true);
