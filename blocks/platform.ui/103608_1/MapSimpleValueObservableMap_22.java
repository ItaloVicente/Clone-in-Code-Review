		ISimplePropertyListener<S, ValueDiff<? extends V>> listener = event -> {
			if (!isDisposed() && !updating) {
				getRealm().exec(() -> {
					@SuppressWarnings("unchecked")
					I source = (I) event.getSource();
					if (event.type == SimplePropertyEvent.CHANGE) {
						notifyIfChanged(source);
					} else if (event.type == SimplePropertyEvent.STALE) {
						boolean wasStale = !staleMasterValues.isEmpty();
						staleMasterValues.add(source);
						if (!wasStale)
							fireStale();
					}
				});
