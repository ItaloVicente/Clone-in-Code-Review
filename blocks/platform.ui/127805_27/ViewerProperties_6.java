	public static <S extends Viewer, E> IViewerValueProperty<S, E> input() {
		return new ViewerInputProperty<>(null);
	}

	public static <S extends Viewer, T> IViewerValueProperty<S, T> input(Class<T> inputType) {
		return new ViewerInputProperty<>(inputType);
