	public static <S extends Viewer> IViewerValueProperty<S, Object> input() {
		return new ViewerInputProperty<>(null);
	}

	public static <S extends Viewer, T> IViewerValueProperty<S, T> input(Class<T> inputType) {
		return new ViewerInputProperty<>(inputType);
