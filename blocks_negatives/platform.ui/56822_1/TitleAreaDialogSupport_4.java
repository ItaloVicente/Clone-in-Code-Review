	private IListChangeListener validationStatusProviderTargetsListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			ListDiff diff = event.diff;
			ListDiffEntry[] differences = diff.getDifferences();
			for (int i = 0; i < differences.length; i++) {
				ListDiffEntry listDiffEntry = differences[i];
				IObservable target = (IObservable) listDiffEntry.getElement();
				if (listDiffEntry.isAddition()) {
					target.addChangeListener(uiChangeListener);
				} else {
					target.removeChangeListener(uiChangeListener);
