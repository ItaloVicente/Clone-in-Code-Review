	@Test
	public void testUntrackedFlag() throws Exception {
		testUtils.addFileToProject(project.getProject(), "foo/untracked.txt",
				"some text");
		String message = "stash with untracked files";
		StashCreateOperation stashCreateOperation = new StashCreateOperation(
				repository, message, true);
		stashCreateOperation.execute(null);

		RevWalk revWalk = new RevWalk(repository);
		RevCommit commit = revWalk.parseCommit(repository.resolve("stash@{0}"));
		assertEquals(commit.getParentCount(), 3);
	}

