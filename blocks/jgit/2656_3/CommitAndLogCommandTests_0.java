
	@Test
	public void testCommitAmend() throws NoHeadException
			UnmergedPathException
			JGitInternalException
		Git git = new Git(db);
		git.commit().setAmend(true).setMessage("first commit").call();

		Iterable<RevCommit> commits = git.log().call();
		int c = 0;
		for (RevCommit commit : commits) {
			assertEquals("first commit"
			c++;
		}
		assertEquals(1
	}
