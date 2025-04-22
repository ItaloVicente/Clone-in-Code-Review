	private IListChangeListener<IObservable> validationStatusProviderTargetsListener = new IListChangeListener<IObservable>() {
		@Override
		public void handleListChange(ListChangeEvent<? extends IObservable> event) {
			for (ListDiffEntry<? extends IObservable> listDiffEntry : event.diff.getDifferences()) {
				if (listDiffEntry.isAddition()) {
					listDiffEntry.getElement().addChangeListener(uiChangeListener);
				} else {
					listDiffEntry.getElement().removeChangeListener(uiChangeListener);
				}
