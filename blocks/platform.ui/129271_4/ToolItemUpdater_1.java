					itemsToUpdateLater.add(ci);
					count++;
					if (count > 100) {
						run();
					} else {
						Display.getDefault().timerExec(DELAY, this);
					}
