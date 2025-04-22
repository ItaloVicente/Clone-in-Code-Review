	public static <K, M, E> IObservableMap<K, E> detailValues(
			IObservableMap<K, M> masterMap,
			IObservableFactory<? super M, IObservableValue<E>> detailFactory,
			Object detailType) {
		return new MapDetailValueObservableMap<K, M, E>(masterMap,
				detailFactory, detailType);
