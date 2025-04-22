	private RevObject enterTree(RevObject obj) throws MissingObjectException
			IncorrectObjectTypeException
		TreeVisit tv = newTreeVisit(obj);
		tv.parent = currVisit;
		currVisit = tv;
		return obj;
	}

	private static int findObjectId(byte[] buf
		for (;;) {
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;

			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
			if (buf[++ptr] == 0) return ++ptr;
