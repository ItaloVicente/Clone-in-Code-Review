	@Test
	public void testDeleteModifyConflict() throws Exception {
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getProject(PROJ1)
				.getFolder(FOLDER).getFile(FILE1);
		try (Git git = new Git(repository)) {
			git.checkout().setCreateBranch(true).setName("side").call();
			assertTrue(file.exists());
			file.delete(true, null);
			assertFalse(file.exists());
			git.rm().addFilepattern(FILE1_PATH).call();
			TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);
			git.commit().setMessage("File deleted").call();
			TestUtil.waitForJobs(50, 5000);

			git.checkout().setName("master").call();
			commitOneFileChange("on master");

			git.merge().include(repository.findRef("side")).call();
		}
		assertEquals(RepositoryState.MERGING, repository.getRepositoryState());

		StagingViewTester stagingView = StagingViewTester.openStagingView();
		assertEquals("", stagingView.getCommitMessage());
		stagingView.assertCommitEnabled(false);

		assertTrue(file.exists());
		file.delete(true, null);
		assertFalse(file.exists());
		TestUtil.joinJobs(JobFamilies.INDEX_DIFF_CACHE_UPDATE);

		stagingView.stageFile(FILE1_PATH);
		assertEquals(RepositoryState.MERGING_RESOLVED,
				repository.getRepositoryState());
		String expectedMessage = "Merge branch 'side'";
		assertThat(stagingView.getCommitMessage(), startsWith(expectedMessage));

		stagingView.commit();
		assertEquals(RepositoryState.SAFE, repository.getRepositoryState());

		assertEquals(expectedMessage,
				TestUtil.getHeadCommit(repository).getShortMessage());

		assertFalse(file.exists());
	}

