		selectionWithProj1Common();
	}

	@Test
	public void selectionWithProj1AndReflog() throws Exception {
		File gitDir = createProjectAndCommitToRepository();

		Git git = new Git(lookupRepository(gitDir));
		git.checkout().setName("stable").call();
		git.checkout().setName("master").call();

		selectionWithProj1Common();
	}

	private void selectionWithProj1Common() {
