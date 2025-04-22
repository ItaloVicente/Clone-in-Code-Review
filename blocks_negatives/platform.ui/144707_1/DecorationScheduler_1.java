				monitor.done();

				if (currentIndex >= listeners.length) {
					resetState();
					if (!hasPendingUpdates()) {
						decorated();
