		public void handleListChange(ListChangeEvent event) {
			ListDiff diff = event.diff;
			for (ListDiffEntry listDiffEntry : diff.getDifferences()) {
				ValidationStatusProvider validationStatusProvider = (ValidationStatusProvider) listDiffEntry
						.getElement();
				IObservableList targets = validationStatusProvider.getTargets();
