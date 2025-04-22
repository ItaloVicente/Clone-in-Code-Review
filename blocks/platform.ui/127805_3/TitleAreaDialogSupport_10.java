		dbc.getValidationStatusProviders().removeListChangeListener(validationStatusProvidersListener);
		for (ValidationStatusProvider provider : dbc.getValidationStatusProviders()) {
			IObservableList<IObservable> targets = provider.getTargets();
			targets.removeListChangeListener(validationStatusProviderTargetsListener);
			for (IObservable observable : targets) {
				observable.removeChangeListener(uiChangeListener);
