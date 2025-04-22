		try (RefCursor rc = seekRef(refName)) {
			return rc.next();
		}
	}

	public boolean hasRefsWithPrefix(String prefix) throws IOException {
		try (RefCursor rc = seekRefsWithPrefix(prefix)) {
			return rc.next();
