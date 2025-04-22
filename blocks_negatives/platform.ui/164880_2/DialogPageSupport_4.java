	private IListChangeListener<ValidationStatusProvider> validationStatusProvidersListener = new IListChangeListener<ValidationStatusProvider>() {
		@Override
		public void handleListChange(ListChangeEvent<? extends ValidationStatusProvider> event) {
			for (ListDiffEntry<? extends ValidationStatusProvider> listDiffEntry : event.diff.getDifferences()) {
				IObservableList<IObservable> targets = listDiffEntry.getElement().getTargets();
				if (listDiffEntry.isAddition()) {
					targets.addListChangeListener(validationStatusProviderTargetsListener);
					for (IObservable observable : targets) {
						observable.addChangeListener(uiChangeListener);
					}
				} else {
					targets.removeListChangeListener(validationStatusProviderTargetsListener);
					for (IObservable observable : targets) {
						observable.removeChangeListener(uiChangeListener);
					}
