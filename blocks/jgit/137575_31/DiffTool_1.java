	private ObjectStream getObjectStream(Pair pair
		ObjectStream stream = null;
		if (!pair.isWorkingTreeSource(side)) {
			try {
				stream = pair.open(side
			} catch (Exception e) {
				stream = null;
			}
		}
		return stream;
	}

	private ContentSource source(AbstractTreeIterator iterator) {
		if (iterator instanceof WorkingTreeIterator) {
			return ContentSource.create((WorkingTreeIterator) iterator);
		}
		return ContentSource.create(db.newObjectReader());
	}

