	public static <M, T> IObservableValue<T> detailValue(
			IObservableValue<M> master,
			IObservableFactory<? super M, IObservableValue<T>> detailFactory,
			Object detailType) {
		return new DetailObservableValue<M, T>(master, detailFactory,
				detailType);
