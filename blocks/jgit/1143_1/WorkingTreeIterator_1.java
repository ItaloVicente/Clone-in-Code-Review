			if (entry.isSmudged()) {
				return contentCheck(entry);
			} else {
				return false;
			}
		}
	}

	private boolean contentCheck(DirCacheEntry entry) {
		if (getEntryObjectId().equals(entry.getObjectId())) {

			entry.setLength((int) getEntryLength());

			return false;
		} else {
			return true;
