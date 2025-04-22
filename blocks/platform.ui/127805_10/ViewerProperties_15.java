	public static <S extends ISelectionProvider> IViewerListProperty<S, Object> multiplePostSelection() {
		return new SelectionProviderMultipleSelectionProperty<>(true);
	}

	public static <S extends ISelectionProvider, T> IViewerListProperty<S, T> multiplePostSelection(Class<T> elemType) {
		return new SelectionProviderMultipleSelectionProperty<>(true);
