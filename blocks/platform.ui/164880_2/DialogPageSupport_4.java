	private IListChangeListener<IObservable> validationStatusProviderTargetsListener = event -> {
		for (ListDiffEntry<? extends IObservable> listDiffEntry : event.diff.getDifferences()) {
			if (listDiffEntry.isAddition()) {
				listDiffEntry.getElement().addChangeListener(uiChangeListener);
			} else {
				listDiffEntry.getElement().removeChangeListener(uiChangeListener);
