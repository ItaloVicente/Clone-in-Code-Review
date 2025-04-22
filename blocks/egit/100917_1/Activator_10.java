		private void mergeEvents(WorkingTreeModifiedEvent oldEvent,
				WorkingTreeModifiedEvent newEvent) {
			oldEvent.getModified().removeAll(newEvent.getDeleted());
			oldEvent.getDeleted().removeAll(newEvent.getModified());
			oldEvent.getModified().addAll(newEvent.getModified());
			oldEvent.getDeleted().addAll(newEvent.getDeleted());
		}

