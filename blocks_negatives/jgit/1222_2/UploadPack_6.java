	private void want(RevObject o) throws MissingObjectException, IOException {
		if (!o.has(WANT)) {
			o.add(WANT);
			wantAll.add(o);

			if (o instanceof RevCommit)
				wantCommits.add((RevCommit) o);
