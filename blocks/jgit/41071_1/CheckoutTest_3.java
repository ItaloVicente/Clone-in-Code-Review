	@Test
	public void testCheckoutOrphan() throws Exception {
		Git git = new Git(db);
		git.commit().setMessage("initial commit").call();

		assertEquals("Switched to a new branch 'new_branch'"
				execute("git checkout --orphan new_branch"));
		assertEquals("refs/heads/new_branch"
		RevCommit commit = git.commit().setMessage("orphan commit").call();
		assertEquals(0
	}

