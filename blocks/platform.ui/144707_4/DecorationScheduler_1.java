					if (currentIndex == NEEDS_INIT) {
						if (hasPendingUpdates()) {
							resetState();
							return Status.OK_STATUS;
						}
						setUpUpdates();
					}

					if (listeners.length == 0) {
