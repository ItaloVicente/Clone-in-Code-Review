	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			Composite composite, IObservable... targetsToBeDecorated) {
		return create(validationStatus, position, composite, new ControlDecorationUpdater(),
				getObservableList(targetsToBeDecorated));
	}

	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			Composite composite, IObservableList<IObservable> targetsToBeDecorated) {
		return create(validationStatus, position, composite, new ControlDecorationUpdater(), targetsToBeDecorated);
	}

