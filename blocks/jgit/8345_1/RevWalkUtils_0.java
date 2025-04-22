	public static void initializeShallowCommits(RevWalk revWalk) throws IOException {
		final File shallow = new File(revWalk.repository.getDirectory()
				"shallow");
		if (!shallow.isFile())
			return;

		final BufferedReader reader = new BufferedReader(
				new FileReader(shallow));
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				final ObjectId id = ObjectId.fromString(line);
				final RevCommit commit = revWalk.lookupCommit(id);
				commit.parents = new RevCommit[0];
			}
		} finally {
			reader.close();
		}
	}
