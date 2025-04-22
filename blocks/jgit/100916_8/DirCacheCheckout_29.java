			try {
				dc.unlock();
			} finally {
				if (performingCheckout) {
					WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
							getUpdated().keySet()
					if (!event.isEmpty()) {
						repo.fireEvent(event);
					}
				}
			}
