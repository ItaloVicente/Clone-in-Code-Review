	private RevObject enterTree(RevObject obj) throws MissingObjectException,
			IncorrectObjectTypeException, IOException {
		TreeVisit tv = newTreeVisit(obj);
		tv.parent = currVisit;
		currVisit = tv;
		return obj;
	}

