	public static <M, K, V> IObservableMap<K, V> detailMap(
			IObservableValue<M> master,
			IObservableFactory<? super M, IObservableMap<K, V>> detailFactory,
			Object detailKeyType, Object detailValueType) {
		return new DetailObservableMap<>(detailFactory, master,
				detailKeyType, detailValueType);
