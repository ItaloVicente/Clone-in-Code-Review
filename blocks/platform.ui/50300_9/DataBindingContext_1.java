	public final <M, T> Binding<?, ?> bindValue(
			IObservableValue<T> targetObservableValue,
			IObservableValue<M> modelObservableValue,
			UpdateValueStrategy<T, M> targetToModel,
			UpdateValueStrategy<M, T> modelToTarget) {
		UpdateValueStrategy<T, M> targetToModelStrategy = targetToModel != null ? targetToModel
				: createTargetToModelUpdateValueStrategy(targetObservableValue,
						modelObservableValue);
		UpdateValueStrategy<M, T> modelToTargetStrategy = modelToTarget != null ? modelToTarget
				: createModelToTargetUpdateValueStrategy(modelObservableValue,
						targetObservableValue);
		targetToModelStrategy.fillDefaults(targetObservableValue,
				modelObservableValue);
		modelToTargetStrategy.fillDefaults(modelObservableValue,
				targetObservableValue);
		ValueBinding<M, T> result = new ValueBinding<>(
				targetObservableValue, modelObservableValue,
				targetToModelStrategy, modelToTargetStrategy);
