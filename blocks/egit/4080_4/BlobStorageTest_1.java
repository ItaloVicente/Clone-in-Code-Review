	@Test
	public void testGitFileHistorySingleProjectOk() throws Exception {
		IProgressMonitor progress = new NullProgressMonitor();
		TestProject singleRepoProject = new TestProject(true, "Project-2");
		IProject proj = singleRepoProject.getProject();
		File singleProjectGitDir = new File(proj.getLocation().toFile(), Constants.DOT_GIT);
		if (singleProjectGitDir.exists())
			FileUtils.delete(singleProjectGitDir, FileUtils.RECURSIVE | FileUtils.RETRY);

		Repository singleProjectRepo = new FileRepository(singleProjectGitDir);
		singleProjectRepo.create();

		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(singleProjectGitDir);
		ConnectProviderOperation connectOp = new ConnectProviderOperation(proj, singleProjectGitDir);
		connectOp.execute(progress);

		try {
			IFile file = proj.getFile("file");
			file.create(new ByteArrayInputStream("data".getBytes()), 0, progress);
			Git git = new Git(singleProjectRepo);
			git.add().addFilepattern(".").call();
			RevCommit commit = git.commit().setAuthor("JUnit", "junit@jgit.org").setAll(true).setMessage("First commit").call();

			GitFileHistoryProvider fhProvider = new GitFileHistoryProvider();
			IFileHistory fh = fhProvider.getFileHistoryFor(singleRepoProject.getProject(), 0, null);
			assertNotNull(fh);
			assertEquals(fh.getFileRevisions().length, 1);
			assertNotNull(fh.getFileRevision(commit.getId().getName()));
		} finally {
			DisconnectProviderOperation disconnectOp = new DisconnectProviderOperation(Collections.singletonList(proj));
			disconnectOp.execute(progress);
			Activator.getDefault().getRepositoryUtil().removeDir(singleProjectGitDir);
			singleProjectRepo.close();
		}
	}

