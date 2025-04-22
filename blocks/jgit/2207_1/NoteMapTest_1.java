
	private static int count(Iterator it) {
		int c = 0;
		while (it.hasNext()) {
			c++;
			it.next();
		}
		return c;
	}
