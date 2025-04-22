		try (Git git = new Git(db)) {
			createTestContent(git);
			String fmt = "tar";
			File archive = new File(getTemporaryDirectory(),
					"archive." + format);
			archive(git, archive, fmt);
			ObjectId hash1 = ObjectId.fromRaw(IO.readFully(archive));
