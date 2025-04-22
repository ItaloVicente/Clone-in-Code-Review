		final RevCommit a2;
		final RevCommit b2;
		try (final RevWalk rw2 = new RevWalk(db)) {
			a2 = rw2.parseCommit(a1);
			b2 = rw2.parseCommit(b1);
		}
