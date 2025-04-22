		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();
			RevCommit commit = git.commit().setMessage("second commit").call();
			git.commit().setMessage("third commit").call();
			Ref tagRef = git.tag().setObjectId(commit).setName("tag")
					.setAnnotated(false).call();
			assertEquals(commit.getId()
		}
