		dbc.getValidationStatusProviders().addListChangeListener(validationStatusProvidersListener);
		for (ValidationStatusProvider provider : dbc.getValidationStatusProviders()) {
			IObservableList<IObservable> targets = provider.getTargets();
			targets.addListChangeListener(validationStatusProviderTargetsListener);
			for (IObservable observable : targets) {
				observable.addChangeListener(uiChangeListener);
