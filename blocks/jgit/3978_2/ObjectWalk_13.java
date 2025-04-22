	private TreeVisit newTreeVisit(RevObject obj) throws LargeObjectException
			MissingObjectException
		TreeVisit tv = freeVisit;
		if (tv != null) {
			freeVisit = tv.parent;
			tv.ptr = 0;
			tv.namePtr = 0;
			tv.nameEnd = 0;
			tv.pathLen = 0;
		} else {
			tv = new TreeVisit();
