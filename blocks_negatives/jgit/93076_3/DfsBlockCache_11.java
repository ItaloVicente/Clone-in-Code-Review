	private static HashEntry clean(HashEntry top) {
		while (top != null && top.ref.next == null)
			top = top.next;
		if (top == null)
			return null;
		HashEntry n = clean(top.next);
		return n == top.next ? top : new HashEntry(n, top.ref);
