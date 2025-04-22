		if (nestedListChangeListener == null) {
			nestedListChangeListener = event -> getRealm().exec(() -> {
				stale = null;
				nestedListChanged(event);
				if (isStale()) {
					fireStale();
				}
			});
		}
		if (topListChangeListener == null) {
			topListChangeListener = event -> getRealm().exec(() -> {
