		try (Git git = new Git(db);
				RevWalk walk = new RevWalk(db)) {
			RevCommit commit = git.commit().setMessage("initial commit").call();
			Ref tagRef = git.tag().setName("tag").call();
			assertEquals(commit.getId()
			assertEquals("tag"
		}
