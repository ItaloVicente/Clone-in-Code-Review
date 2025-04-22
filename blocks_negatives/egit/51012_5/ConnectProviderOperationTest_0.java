		p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources, true);
		Repository repository = FileRepositoryBuilder.create(gitDir);
		repository.create();
		repository.close();
		project.setBinFolderDerived();
		ConnectProviderOperation operation = new ConnectProviderOperation(
				project.getProject(), gitDir);
		operation.execute(null);

		assertTrue(RepositoryProvider.isShared(project.getProject()));
		Job.getJobManager().join(JobFamilies.AUTO_IGNORE, null);
