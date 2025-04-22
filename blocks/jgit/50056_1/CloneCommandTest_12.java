		try (
				Git git2 = Git.cloneRepository()
						.setBranch("tag-initial")
						.setDirectory(directory)
						.setURI(fileUri())
						.call();
				Repository db2 = git2.getRepository()) {
			ObjectId taggedCommit = db.resolve("tag-initial^{commit}");
			assertEquals(taggedCommit.name()
		}
