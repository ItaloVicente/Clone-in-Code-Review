		c.setParentIds(parents);
		c.setAuthor(mockAuthor(parents));
		c.setCommitter(c.getAuthor());
		return RevCommit.parse(walk
	}

	private static PersonIdent mockAuthor(List<RevCommit> parents) {
		String name = RecursiveMerger.class.getSimpleName();
		int time = 0;
		for (RevCommit p : parents)
			time = Math.max(time
		return new PersonIdent(
				name
				new Date((time + 1) * 1000L)
