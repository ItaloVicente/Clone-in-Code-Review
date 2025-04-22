	private IMapChangeListener targetChangeListener = new IMapChangeListener() {
		@Override
		public void handleMapChange(MapChangeEvent event) {
			if (updatingTarget)
				return;
			IStatus status = (IStatus) validationStatus.getValue();
			if (isValid(status)) {
				if (stale) {
					stale = false;
					updateWrappedMap(new HashMap(target));
				} else {
					MapDiff diff = event.diff;
					if (computeNextDiff) {
						diff = Diffs.computeMapDiff(wrappedMap, target);
						computeNextDiff = false;
					}
					applyDiff(diff, wrappedMap);
					fireMapChange(diff);
				}
