		try (Git git = new Git(db)) {
			git.commit().setMessage("initial commit").call();

			assertStringArrayEquals("Switched to a new branch 'new_branch'"
					execute("git checkout --orphan new_branch"));
			assertEquals("refs/heads/new_branch"
					db.exactRef("HEAD").getTarget().getName());
			RevCommit commit = git.commit().setMessage("orphan commit").call();
			assertEquals(0
		}
