	private static boolean idEqual(AbstractTreeIterator a
			AbstractTreeIterator b) {
		if (a == b) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.getEntryObjectId().equals(b.getEntryObjectId());
	}

