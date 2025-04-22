				try {
					long timeout = getTimeout();
					if (timeout <= 0) {
						timeout = 10;
					}
					process.waitFor(timeout
				} catch (InterruptedException e) {
				}
