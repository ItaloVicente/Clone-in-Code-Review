			listener = property.adaptListener(new ISimplePropertyListener<S, ValueDiff<? extends T>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, ValueDiff<? extends T>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
							@Override
							public void run() {
								if (event.type == SimplePropertyEvent.CHANGE) {
									notifyIfChanged(event.diff);
								} else if (event.type == SimplePropertyEvent.STALE && !stale) {
									stale = true;
									fireStale();
								}
							}
						});
					}
