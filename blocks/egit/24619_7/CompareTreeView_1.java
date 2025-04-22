	private long getEntrySize(TreeWalk tw, AbstractTreeIterator iterator)
			throws MissingObjectException, IncorrectObjectTypeException,
			IOException {
		if (iterator instanceof ContainerTreeIterator)
			return ((ContainerTreeIterator) iterator).getEntryContentLength();
		if (iterator instanceof FileTreeIterator)
			return ((FileTreeIterator) iterator).getEntryContentLength();
		try {
			return tw.getObjectReader().getObjectSize(
					iterator.getEntryObjectId(), Constants.OBJ_BLOB);
		} catch (MissingObjectException e) {
			return 0;
		}
	}

