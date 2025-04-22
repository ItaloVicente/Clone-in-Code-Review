	public static <M, E> IObservableSet<E> detailSet(
			IObservableValue<M> master,
			IObservableFactory<? super M, IObservableSet<E>> detailFactory,
			Object detailElementType) {
		return new DetailObservableSet<>(detailFactory, master,
				detailElementType);
