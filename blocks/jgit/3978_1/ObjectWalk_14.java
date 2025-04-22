		tv.obj = obj;
		tv.buf = reader.open(obj
		return tv;
	}

	private void releaseTreeVisit(TreeVisit tv) {
		tv.buf = null;
		tv.parent = freeVisit;
		freeVisit = tv;
	}

	private static class TreeVisit {
		TreeVisit parent;

		RevObject obj;

		byte[] buf;

		int ptr;

		int namePtr;

		int nameEnd;

		int pathLen;
