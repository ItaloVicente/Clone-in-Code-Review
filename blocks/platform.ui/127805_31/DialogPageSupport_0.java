		dbc.getValidationStatusProviders().addListChangeListener(validationStatusProvidersListener);
		for (ValidationStatusProvider validationStatusProvider : dbc.getValidationStatusProviders()) {
			IObservableList<IObservable> targets = validationStatusProvider.getTargets();
			targets.addListChangeListener(validationStatusProviderTargetsListener);
			for (IObservable observable : targets) {
				observable.addChangeListener(uiChangeListener);
