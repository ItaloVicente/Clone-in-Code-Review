
	@Test
	public void testRefreshRepositoryResources() throws Exception {
		TestProject subdirProject = new TestProject(true, "subdir/Project-2");

		new ConnectProviderOperation(project.getProject(), repository
				.getRepository().getDirectory()).execute(null);
		new ConnectProviderOperation(subdirProject.getProject(), repository
				.getRepository().getDirectory()).execute(null);

		IFile file1 = createOutOfSyncFile(project, "refresh1");
		ProjectUtil.refreshRepositoryResources(repository.getRepository(),
				Arrays.asList("Project-1/refresh1"), new NullProgressMonitor());
		assertTrue(file1.isSynchronized(IResource.DEPTH_ZERO));

		IFile file2 = createOutOfSyncFile(project, "refresh2");
		ProjectUtil.refreshRepositoryResources(repository.getRepository(),
				Arrays.asList("Project-1"), new NullProgressMonitor());
		assertTrue(file2.isSynchronized(IResource.DEPTH_ZERO));

		IFile file3 = createOutOfSyncFile(project, "refresh3");
		ProjectUtil.refreshRepositoryResources(repository.getRepository(),
				Arrays.asList(""), new NullProgressMonitor());
		assertTrue(file3.isSynchronized(IResource.DEPTH_ZERO));

		IFile file4 = createOutOfSyncFile(subdirProject, "refresh4");
		ProjectUtil.refreshRepositoryResources(repository.getRepository(),
				Arrays.asList("subdir"), new NullProgressMonitor());
		assertTrue(file4.isSynchronized(IResource.DEPTH_ZERO));
	}

	private static IFile createOutOfSyncFile(TestProject project, String name)
			throws IOException, CoreException {
		IFile file = project.getProject().getFile(name);
		file.create(new ByteArrayInputStream("test".getBytes("UTF-8")), false,
				null);
		assertTrue(file.isSynchronized(IResource.DEPTH_ZERO));
		assertTrue(file.getLocation().toFile().delete());
		assertFalse(file.isSynchronized(IResource.DEPTH_ZERO));
		return file;
	}
