	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			IObservable... targetsToBeDecorated) {
		return create(validationStatus, position, null, new ControlDecorationUpdater(),
				getObservableList(targetsToBeDecorated));
	}

	public static ControlDecorationSupport create(IObservableValue<IStatus> validationStatus, int position,
			IObservableList<IObservable> targetsToBeDecorated) {
		return create(validationStatus, position, null, new ControlDecorationUpdater(), targetsToBeDecorated);
	}

