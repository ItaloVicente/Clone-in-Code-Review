	public void archiveHeadAllFilesTgzTimestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tgz";
			File archive = new File(getTemporaryDirectory(),
					"archive." + fmt);
			archive(git, archive, fmt);
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));
