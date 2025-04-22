
	public void initializeShallowCommits() throws IOException {
		if (shallowCommitsInitialized)
			return;

		if (walker.repository == null)
			return;

		final File gitDir = walker.repository.getDirectory();
		if (gitDir == null)
			return;

		final File shallow = new File(gitDir
		if (!shallow.isFile())
			return;

		final BufferedReader reader = new BufferedReader(
				new FileReader(shallow));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				final ObjectId id = ObjectId.fromString(line);
				final RevCommit commit = walker.lookupCommit(id);
				commit.parents = new RevCommit[0];
			}
		} finally {
			reader.close();
		}
	}
