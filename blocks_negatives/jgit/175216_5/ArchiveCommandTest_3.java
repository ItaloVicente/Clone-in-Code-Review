	public void archiveHeadAllFilesTbz2Timestamps() throws Exception {
		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tbz2";
			File archive = new File(getTemporaryDirectory(),
					"archive." + fmt);
			archive(git, archive, fmt);
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));
