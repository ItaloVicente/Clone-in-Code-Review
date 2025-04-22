	public final Binding bindList(IObservableList targetObservableList,
			IObservableList modelObservableList,
			UpdateListStrategy targetToModel, UpdateListStrategy modelToTarget) {
		UpdateListStrategy targetToModelStrategy = targetToModel != null ? targetToModel
				: createTargetToModelUpdateListStrategy(targetObservableList,
						modelObservableList);
		UpdateListStrategy modelToTargetStrategy = modelToTarget != null ? modelToTarget
				: createModelToTargetUpdateListStrategy(modelObservableList,
						targetObservableList);
