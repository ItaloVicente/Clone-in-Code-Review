	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator)
			return ContentSource.create((WorkingTreeIterator) iterator);
		return ContentSource.create(reader);
	}

