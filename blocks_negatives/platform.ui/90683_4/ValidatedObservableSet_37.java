	private ISetChangeListener targetChangeListener = new ISetChangeListener() {
		@Override
		public void handleSetChange(SetChangeEvent event) {
			if (updatingTarget)
				return;
			IStatus status = (IStatus) validationStatus.getValue();
			if (isValid(status)) {
				if (stale) {
					stale = false;
					updateWrappedSet(new HashSet(target));
				} else {
					SetDiff diff = event.diff;
					if (computeNextDiff) {
						diff = Diffs.computeSetDiff(wrappedSet, target);
						computeNextDiff = false;
					}
					applyDiff(diff, wrappedSet);
					fireSetChange(diff);
				}
