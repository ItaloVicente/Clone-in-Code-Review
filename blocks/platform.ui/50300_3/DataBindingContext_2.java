	public final <M, T> Binding<?, ?> bindList(
			IObservableList<T> targetObservableList,
			IObservableList<M> modelObservableList,
			UpdateListStrategy<T, M> targetToModel,
			UpdateListStrategy<M, T> modelToTarget) {
		UpdateListStrategy<T, M> targetToModelStrategy = targetToModel != null ? targetToModel
