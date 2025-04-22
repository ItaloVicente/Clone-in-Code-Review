		return create(validationStatusProvider.getValidationStatus(), position, composite, updater,
				validationStatusProvider.getTargets());
	}

	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			Composite composite, ControlDecorationUpdater updater, IObservable... targetsToBeDecorated) {
		return create(validationStatus, position, composite, updater, getObservableList(targetsToBeDecorated));
	}

	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			Composite composite, ControlDecorationUpdater updater, IObservableList<IObservable> targetsToBeDecorated) {
		return new ControlDecorationSupport(validationStatus, targetsToBeDecorated, position, composite, updater);
