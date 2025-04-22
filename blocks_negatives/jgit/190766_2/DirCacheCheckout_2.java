	private void addTree(TreeWalk tw, ObjectId id) throws MissingObjectException, IncorrectObjectTypeException, IOException {
		if (id == null)
			tw.addTree(new EmptyTreeIterator());
		else
			tw.addTree(id);
	}

