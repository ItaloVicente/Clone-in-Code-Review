		if (columnIndex == 0) {
			final StagingEntry c = (StagingEntry) element;
			if (c.getState() == StagingEntry.State.MODIFIED || c.getState() == StagingEntry.State.PARTIALLY_MODIFIED)
			else
				return c.getPath();
		}
		return null;
