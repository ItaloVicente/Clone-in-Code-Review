			} else if (ConflictModel.PROP_CONFLICTS.equals(event
					.getProperty())) {
				conflictViewer.setInput(event.getNewValue());
			} else if (ConflictModel.PROP_CONFLICTS_ADD.equals(event
					.getProperty())) {
				conflictViewer.add(event.getNewValue());
			} else if (ConflictModel.PROP_CONFLICTS_REMOVE.equals(event
					.getProperty())) {
				conflictViewer.remove(event.getNewValue());
