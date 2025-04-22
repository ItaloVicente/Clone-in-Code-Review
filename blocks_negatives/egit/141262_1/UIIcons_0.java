		IProgressService service = PlatformUI.getWorkbench()
				.getProgressService();

		service.registerIconForFamily(PULL, JobFamilies.PULL);
		service.registerIconForFamily(REPOSITORY, JobFamilies.AUTO_IGNORE);
		service.registerIconForFamily(REPOSITORY, JobFamilies.AUTO_SHARE);
		service.registerIconForFamily(REPOSITORY,
				JobFamilies.INDEX_DIFF_CACHE_UPDATE);
		service.registerIconForFamily(REPOSITORY,
				JobFamilies.REPOSITORY_CHANGED);
