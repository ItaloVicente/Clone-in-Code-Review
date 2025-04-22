	public final Binding bindValue(IObservableValue targetObservableValue,
			IObservableValue modelObservableValue,
			UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		UpdateValueStrategy targetToModelStrategy = targetToModel != null ? targetToModel
						: createTargetToModelUpdateValueStrategy(targetObservableValue, modelObservableValue);
		UpdateValueStrategy modelToTargetStrategy = modelToTarget != null ? modelToTarget
				: createModelToTargetUpdateValueStrategy(modelObservableValue, targetObservableValue);
		targetToModelStrategy.fillDefaults(targetObservableValue, modelObservableValue);
		modelToTargetStrategy.fillDefaults(modelObservableValue, targetObservableValue);
		ValueBinding result = new ValueBinding(targetObservableValue,
				modelObservableValue, targetToModelStrategy,
				modelToTargetStrategy);
