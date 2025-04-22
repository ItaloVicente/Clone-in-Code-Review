					itemsToUpdateLater.add(ci);
					count++;
					if (count > 100) {
						doRunNow = true;
					} else {
						Display.getDefault().timerExec(DELAY, this);
					}
