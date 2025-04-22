
	private void registerCoreJobFamilyIcons() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(() -> {
			IProgressService service = PlatformUI.getWorkbench()
					.getProgressService();

			service.registerIconForFamily(UIIcons.PULL, JobFamilies.PULL);
			service.registerIconForFamily(UIIcons.REPOSITORY,
					JobFamilies.AUTO_IGNORE);
			service.registerIconForFamily(UIIcons.REPOSITORY,
					JobFamilies.AUTO_SHARE);
			service.registerIconForFamily(UIIcons.REPOSITORY,
					JobFamilies.INDEX_DIFF_CACHE_UPDATE);
			service.registerIconForFamily(UIIcons.REPOSITORY,
					JobFamilies.REPOSITORY_CHANGED);
		});
	}
