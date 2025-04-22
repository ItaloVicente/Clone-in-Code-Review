		public void handleChange(ChangeEvent event) {
			handleUIChanged();
		}
	};
	private IListChangeListener validationStatusProvidersListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			ListDiff diff = event.diff;
			for (ListDiffEntry listDiffEntry : diff.getDifferences()) {
				ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider) listDiffEntry
						.getElement();
				IObservableList targets = validationStatusProvider.getTargets();
