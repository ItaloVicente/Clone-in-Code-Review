				try {
					synchronized (DecorationScheduler.this) {
						if (shutdown) {
							return Status.CANCEL_STATUS;
						}
					}

					if (currentIndex == NEEDS_INIT) {
						if (hasPendingUpdates()) {
							resetState();
							return Status.OK_STATUS;
						}
						setUpUpdates();
