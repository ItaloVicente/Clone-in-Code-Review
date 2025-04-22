		dbc.getValidationStatusProviders().removeListChangeListener(
				validationStatusProvidersListener);
		for (Iterator it = dbc.getValidationStatusProviders().iterator(); it
				.hasNext();) {
			ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider) it
					.next();
			IObservableList targets = validationStatusProvider.getTargets();
			targets
					.removeListChangeListener(validationStatusProviderTargetsListener);
			for (Iterator iter = targets.iterator(); iter.hasNext();) {
				((IObservable) iter.next())
						.removeChangeListener(uiChangeListener);
