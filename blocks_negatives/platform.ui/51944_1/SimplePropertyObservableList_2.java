							public void handleEvent(
									final SimplePropertyEvent event) {
								if (!isDisposed() && !updating) {
									getRealm().exec(new Runnable() {
										@Override
										public void run() {
											if (event.type == SimplePropertyEvent.CHANGE) {
												modCount++;
												notifyIfChanged((ListDiff) event.diff);
											} else if (event.type == SimplePropertyEvent.STALE
													&& !stale) {
												stale = true;
												fireStale();
											}
										}
									});
