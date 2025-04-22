	public static <M, E> IObservableList<E> detailValues(
			IObservableList<M> masterList,
			IObservableFactory<? super M, IObservableValue<E>> detailFactory,
			Object detailType) {
		return new ListDetailValueObservableList<>(masterList,
				detailFactory, detailType);
