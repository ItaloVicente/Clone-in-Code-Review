	public final <M, T> Binding bindList(
			IObservableList<T> targetObservableList,
			IObservableList<M> modelObservableList,
			UpdateListStrategy<? super T, ? extends M> targetToModel,
			UpdateListStrategy<? super M, ? extends T> modelToTarget) {
		UpdateListStrategy<? super T, ? extends M> targetToModelStrategy = targetToModel != null ? targetToModel
				: createTargetToModelUpdateListStrategy(targetObservableList, modelObservableList);
		UpdateListStrategy<? super M, ? extends T> modelToTargetStrategy = modelToTarget != null ? modelToTarget
				: createModelToTargetUpdateListStrategy(modelObservableList, targetObservableList);
