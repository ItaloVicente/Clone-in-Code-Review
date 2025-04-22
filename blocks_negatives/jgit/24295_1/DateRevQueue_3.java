	public String toString() {
		final StringBuilder s = new StringBuilder();
		for (Entry q = head; q != null; q = q.next)
			describe(s, q.commit);
		return s.toString();
	}

	private Entry newEntry(final RevCommit c) {
		Entry r = free;
		if (r == null)
			r = new Entry();
		else
			free = r.next;
		r.commit = c;
		return r;
