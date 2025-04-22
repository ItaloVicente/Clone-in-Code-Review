	@Test
	public void testIndexRevision() throws Exception {
		GitProvider provider = (GitProvider)RepositoryProvider.getProvider(project.project);
		assertNotNull(provider);
		IFileHistoryProvider fileHistoryProvider = provider.getFileHistoryProvider();
		IFileHistory fileHistory = fileHistoryProvider.getFileHistoryFor(project.getProject().getWorkspace().getRoot().findMember("Project-1/A.txt"), IFileHistoryProvider.SINGLE_LINE_OF_DESCENT, new NullProgressMonitor());
		IFileRevision fileRevision = fileHistory.getFileRevision(GitFileRevision.INDEX);
		assertEquals(GitFileRevision.INDEX, fileRevision.getContentIdentifier());
		IStorage storage = fileRevision.getStorage(null);
		String content = testUtils.slurpAndClose(storage.getContents());
		assertEquals("A.txt - first version\n", content);
	}

	@Test
	public void testIndexRevisionSecondCommit() throws Exception {
		GitProvider provider = (GitProvider)RepositoryProvider.getProvider(project.project);
		assertNotNull(provider);
		IFileHistoryProvider fileHistoryProvider = provider.getFileHistoryProvider();
		IFileHistory fileHistory = fileHistoryProvider.getFileHistoryFor(project.getProject().getWorkspace().getRoot().findMember("Project-1/B.txt"), IFileHistoryProvider.SINGLE_LINE_OF_DESCENT, new NullProgressMonitor());
		IFileRevision fileRevision = fileHistory.getFileRevision(GitFileRevision.INDEX);
		assertEquals(GitFileRevision.INDEX, fileRevision.getContentIdentifier());
		IStorage storage = fileRevision.getStorage(null);
		String content = testUtils.slurpAndClose(storage.getContents());
		assertEquals("B.txt - second version\n", content);
	}
