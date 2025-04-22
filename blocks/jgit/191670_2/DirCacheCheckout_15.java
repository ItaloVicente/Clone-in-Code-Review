	private int addTree(TreeWalk tw
			IncorrectObjectTypeException
		if (id == null) {
			return tw.addTree(new EmptyTreeIterator());
		}
		return tw.addTree(id);
	}

