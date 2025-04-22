		ISimplePropertyListener<S, ValueDiff<? extends T>> listener = new ISimplePropertyListener<S, ValueDiff<? extends T>>() {
			@Override
			public void handleEvent(final SimplePropertyEvent<S, ValueDiff<? extends T>> event) {
				if (!isDisposed() && !updating) {
					getRealm().exec(new Runnable() {
						@Override
						public void run() {
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
						}
					});
				}
