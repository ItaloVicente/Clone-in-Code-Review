		ISourceProviderService sps = PlatformUI.getWorkbench()
				.getService(ISourceProviderService.class);
		if (sps == null) {
			return false;
		}
		RepositorySourceProvider sp = (RepositorySourceProvider) sps
				.getSourceProvider(
						RepositorySourceProvider.REPOSITORY_PROPERTY);
		if (sp == null) {
			return false;
		}
		Repository repository = sp.waitFor();
