			WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
					actuallyModifiedPaths
			actuallyModifiedPaths = null;
			actuallyDeletedPaths = null;
			try {
				dc.unlock();
			} finally {
				if (!event.isEmpty()) {
					repo.fireEvent(event);
				}
			}
