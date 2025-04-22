		final RevCommit first;
		try {
			first = RevWalk.this.next();
		} catch (IOException e) {
			throw new RevWalkException(e);
		}
