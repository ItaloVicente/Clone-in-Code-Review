		final Map<ObjectId, String> missing = new HashMap<ObjectId, String>();
		final List<RevObject> commits = new ArrayList<RevObject>();
		for (final Map.Entry<ObjectId, String> e : prereqs.entrySet()) {
			ObjectId p = e.getKey();
			try {
				final RevCommit c = rw.parseCommit(p);
				if (!c.has(PREREQ)) {
					c.add(PREREQ);
					commits.add(c);
