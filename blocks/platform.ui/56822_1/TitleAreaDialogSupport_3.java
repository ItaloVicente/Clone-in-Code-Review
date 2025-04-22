
	private IListChangeListener validationStatusProvidersListener = event -> {
		ListDiff diff = event.diff;
		ListDiffEntry[] differences = diff.getDifferences();
		for (int i = 0; i < differences.length; i++) {
			ListDiffEntry listDiffEntry = differences[i];
			ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider) listDiffEntry
					.getElement();
			IObservableList targets = validationStatusProvider.getTargets();
			if (listDiffEntry.isAddition()) {
				targets
						.addListChangeListener(validationStatusProviderTargetsListener);
				for (Iterator it1 = targets.iterator(); it1.hasNext();) {
					((IObservable) it1.next())
							.addChangeListener(uiChangeListener);
				}
			} else {
				targets
						.removeListChangeListener(validationStatusProviderTargetsListener);
				for (Iterator it2 = targets.iterator(); it2.hasNext();) {
					((IObservable) it2.next())
							.removeChangeListener(uiChangeListener);
