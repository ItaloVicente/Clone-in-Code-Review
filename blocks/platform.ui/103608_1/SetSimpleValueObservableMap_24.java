			listener = detailProperty.adaptListener(event -> {
				if (!isDisposed() && !updating) {
					getRealm().exec(() -> {
						K source = (K) event.getSource();
						if (event.type == SimplePropertyEvent.CHANGE) {
							notifyIfChanged(source);
						} else if (event.type == SimplePropertyEvent.STALE) {
							boolean wasStale = !staleKeys.isEmpty();
							staleKeys.add(source);
							if (!wasStale)
								fireStale();
						}
					});
