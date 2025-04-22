							public void run() {
								if (event.type == SimplePropertyEvent.CHANGE) {
									notifyIfChanged(event.diff);
								} else if (event.type == SimplePropertyEvent.STALE && !stale) {
									stale = true;
									fireStale();
