			listener = property.adaptListener(event -> {
				if (!isDisposed() && !updating) {
					getRealm().exec(() -> {
						if (event.type == SimplePropertyEvent.CHANGE) {
							modCount++;
							notifyIfChanged(event.diff);
						} else if (event.type == SimplePropertyEvent.STALE && !stale) {
							stale = true;
							fireStale();
						}
					});
