		try (Git git = new Git(db)) {
			git.branchCreate().setName("initial").call();
			RevCommit second = git.commit().setMessage("second commit")
					.call();
			assertEquals(toString("  initial"
					toString(execute("git branch --contains 6fd41be")));
			assertEquals("* master"
					toString(execute("git branch --contains " + second.name())));
		}
