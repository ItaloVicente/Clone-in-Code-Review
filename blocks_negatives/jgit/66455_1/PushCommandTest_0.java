		Git git1 = new Git(db);
		RevCommit commit = git1.commit().setMessage("initial commit").call();
		Ref tagRef = git1.tag().setName("tag").call();

		try {
			db2.resolve(commit.getId().getName() + "^{commit}");
			fail("id shouldn't exist yet");
		} catch (MissingObjectException e) {
