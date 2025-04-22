		boolean autoignore = p.getBoolean(
				GitCorePreferences.core_autoIgnoreDerivedResources, false);
		if (!autoignore) {
			p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
					true);
		}
		try {
			Repository repository = FileRepositoryBuilder.create(gitDir);
			repository.create();
			repository.close();
			project.setBinFolderDerived();
			project.createSourceFolder();

			IFolder binFolder = project.getProject().getFolder("bin");
			IPath binPath = binFolder.getLocation();
			assertTrue(binFolder.exists());
			assertFalse(RepositoryUtil.canBeAutoIgnored(binPath));

			IFolder srcFolder = project.getProject().getFolder("src");
			IPath srcPath = srcFolder.getLocation();
			assertTrue(srcFolder.exists());
			assertFalse(RepositoryUtil.canBeAutoIgnored(srcPath));

			IFolder notThere = project.getProject().getFolder("notThere");
			IPath notTherePath = notThere.getLocation();
			assertFalse(notThere.exists());
			assertFalse(RepositoryUtil.canBeAutoIgnored(notTherePath));

			ConnectProviderOperation operation = new ConnectProviderOperation(
					project.getProject(), gitDir);
			operation.execute(null);
			assertTrue(RepositoryProvider.isShared(project.getProject()));
			Job.getJobManager().join(JobFamilies.AUTO_IGNORE, null);

			assertFalse(RepositoryUtil.canBeAutoIgnored(binPath));
			assertTrue(RepositoryUtil.canBeAutoIgnored(srcPath));
			assertFalse(RepositoryUtil.canBeAutoIgnored(notTherePath));

			assertTrue(gitDir.exists());
		} finally {
			if (!autoignore) {
				p.putBoolean(GitCorePreferences.core_autoIgnoreDerivedResources,
						false);
			}
		}
	}
