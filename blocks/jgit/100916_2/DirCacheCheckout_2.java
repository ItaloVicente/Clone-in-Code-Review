			try {
				dc.unlock();
			} finally {
				WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
						getUpdated().keySet()
				if (!event.isEmpty()) {
					repo.fireEvent(event);
				}
			}
