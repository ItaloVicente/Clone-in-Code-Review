			listener = property.adaptListener(new ISimplePropertyListener<S, SetDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, SetDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
							@Override
							public void run() {
								if (event.type == SimplePropertyEvent.CHANGE) {
									modCount++;
									notifyIfChanged(event.diff);
								} else if (event.type == SimplePropertyEvent.STALE && !stale) {
									stale = true;
									fireStale();
								}
							}
						});
					}
