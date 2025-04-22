	private IListChangeListener targetChangeListener = new IListChangeListener() {
		@Override
		public void handleListChange(ListChangeEvent event) {
			if (updatingTarget)
				return;
			IStatus status = (IStatus) validationStatus.getValue();
			if (isValid(status)) {
				if (stale) {
					stale = false;
					updateWrappedList(new ArrayList(target));
				} else {
					ListDiff diff = event.diff;
					if (computeNextDiff) {
						diff = Diffs.computeListDiff(wrappedList, target);
						computeNextDiff = false;
					}
					applyDiff(diff, wrappedList);
					fireListChange(diff);
				}
