			listener = property.adaptListener(new ISimplePropertyListener<S, ListDiff<E>>() {
				@Override
				public void handleEvent(final SimplePropertyEvent<S, ListDiff<E>> event) {
					if (!isDisposed() && !updating) {
						getRealm().exec(new Runnable() {
							@Override
							public void run() {
								if (event.type == SimplePropertyEvent.CHANGE) {
									simplePropertyModCount++;
									notifyIfChanged(event.diff);
								} else if (event.type == SimplePropertyEvent.STALE && !stale) {
									stale = true;
									fireStale();
								}
							}
						});
					}
