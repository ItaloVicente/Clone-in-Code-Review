		ISimplePropertyListener<S, ValueDiff<? extends V>> listener = new ISimplePropertyListener<S, ValueDiff<? extends V>>() {
			@Override
			public void handleEvent(final SimplePropertyEvent<S, ValueDiff<? extends V>> event) {
				if (!isDisposed() && !updating) {
					getRealm().exec(new Runnable() {
						@Override
						public void run() {
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
						}
					});
				}
