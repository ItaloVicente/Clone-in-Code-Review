		ISimplePropertyListener<S, ValueDiff<? extends T>> listener = event -> {
			if (!isDisposed() && !updating) {
				getRealm().exec(() -> {
					@SuppressWarnings("unchecked")
					M source = (M) event.getSource();
					if (event.type == SimplePropertyEvent.CHANGE) {
						notifyIfChanged(source);
					} else if (event.type == SimplePropertyEvent.STALE) {
						boolean wasStale = !staleElements.isEmpty();
						staleElements.add(source);
						if (!wasStale)
							fireStale();
					}
				});
