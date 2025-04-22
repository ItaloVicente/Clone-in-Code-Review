	public final <M, T> Binding<IObservableValue<M>, IObservableValue<T>> bindValue(
			IObservableValue<T> targetObservableValue, IObservableValue<M> modelObservableValue,
			UpdateValueStrategy<T, M> targetToModel,
			UpdateValueStrategy<M, T> modelToTarget) {
		UpdateValueStrategy<T, M> targetToModelStrategy = targetToModel != null ? targetToModel
				: createTargetToModelUpdateValueStrategy(targetObservableValue, modelObservableValue);
		UpdateValueStrategy<M, T> modelToTargetStrategy = modelToTarget != null ? modelToTarget
