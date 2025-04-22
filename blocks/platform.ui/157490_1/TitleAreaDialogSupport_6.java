	private IChangeListener uiChangeListener = event -> handleUIChanged();
	private IListChangeListener<ValidationStatusProvider> validationStatusProvidersListener = event -> {
		for (ListDiffEntry<? extends ValidationStatusProvider> listDiffEntry : event.diff.getDifferences()) {
			IObservableList<IObservable> targets = listDiffEntry.getElement().getTargets();
			if (listDiffEntry.isAddition()) {
				targets.addListChangeListener(validationStatusProviderTargetsListener);
				for (IObservable observable1 : targets) {
					observable1.addChangeListener(uiChangeListener);
				}
			} else {
				targets.removeListChangeListener(validationStatusProviderTargetsListener);
				for (IObservable observable2 : targets) {
					observable2.removeChangeListener(uiChangeListener);
