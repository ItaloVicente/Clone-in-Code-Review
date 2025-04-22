	@Test
	public void testAutoIgnoresDerivedFolder() throws Exception {
		IEclipsePreferences p = InstanceScope.INSTANCE.getNode(Activator
				.getPluginId());
		p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources, true);
		Repository repository = new FileRepository(gitDir);
		repository.create();
		repository.close();
		project.setBinFolderDerived();
		ConnectProviderOperation operation = new ConnectProviderOperation(
				project.getProject(), gitDir);
		operation.execute(null);

		assertTrue(RepositoryProvider.isShared(project.getProject()));
		Job.getJobManager().join(JobFamilies.AUTO_IGNORE, null);

		IPath binPath = project.getProject().getLocation().append("bin");
		assertTrue(RepositoryUtil.isIgnored(binPath));
		assertTrue(gitDir.exists());
		p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources, false);
	}

