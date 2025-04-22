					itemsToUpdateLater.add(ci);
					count++;
					if (count > 100) {
						run(); // runnable was not called the last 100 trigger times, do it now
						count = 0;
					}
					else
						Display.getDefault().timerExec(DELAY, this);
