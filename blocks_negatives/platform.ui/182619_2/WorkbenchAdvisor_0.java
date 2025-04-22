					yield();
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
					}
					display.wake();
