					itemsToUpdateLater.add(ci);
					if (timestampOfEarliestQueuedUpdate == 0) {
						timestampOfEarliestQueuedUpdate = System.nanoTime();
					}
					if (System.nanoTime() - timestampOfEarliestQueuedUpdate > DELAY * 1_000_000) {
						doRunNow = true;
					} else {
						Display.getDefault().timerExec(DELAY, this);
					}
