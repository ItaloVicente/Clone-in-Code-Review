			try {
				dc.unlock();
			} finally {
				WorkingTreeModifiedEvent event = new WorkingTreeModifiedEvent(
						actuallyModifiedPaths
				actuallyModifiedPaths = null;
				if (!event.isEmpty()) {
					repo.fireEvent(event);
				}
			}
