	public final <T, M> Binding bindValue(
			IObservableValue<T> targetObservableValue, IObservableValue<M> modelObservableValue,
			UpdateValueStrategy<? super T, ? extends M> targetToModel,
			UpdateValueStrategy<? super M, ? extends T> modelToTarget) {
		UpdateValueStrategy<? super T, ? extends M> targetToModelStrategy = targetToModel != null ? targetToModel
				: createTargetToModelUpdateValueStrategy(targetObservableValue, modelObservableValue);
		UpdateValueStrategy<? super M, ? extends T> modelToTargetStrategy = modelToTarget != null ? modelToTarget
