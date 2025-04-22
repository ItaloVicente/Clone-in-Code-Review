	public final Binding bindValue(IObservableValue targetObservableValue,
			IObservableValue modelObservableValue,
			UpdateValueStrategy targetToModel, UpdateValueStrategy modelToTarget) {
		UpdateValueStrategy targetToModelStrategy = targetToModel != null ? targetToModel
						: createTargetToModelUpdateValueStrategy(targetObservableValue, modelObservableValue);
		UpdateValueStrategy modelToTargetStrategy = modelToTarget != null ? modelToTarget
