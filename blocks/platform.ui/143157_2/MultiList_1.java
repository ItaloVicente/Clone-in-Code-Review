		if (topListChangeListener == null) {
			topListChangeListener = event -> {
				getRealm().exec(() -> {
					stale = null;
					topListChanged(event);
					if (isStale()) {
						fireStale();
					}
				});
			};
		}
