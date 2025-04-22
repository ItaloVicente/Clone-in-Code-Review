	private boolean isEqualEntry(AbstractTreeIterator iter1
			AbstractTreeIterator iter2) {
		if (!iter1.getEntryFileMode().equals(iter2.getEntryFileMode()))
			return false;
		ObjectId id1 = iter1.getEntryObjectId();
		ObjectId id2 = iter2.getEntryObjectId();
		return id1 != null ? id1.equals(id2) : id2 == null;
	}

