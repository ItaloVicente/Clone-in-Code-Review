		ISourceProviderService sps = PlatformUI.getWorkbench()
				.getService(ISourceProviderService.class);
		RepositorySourceProvider rsp = (RepositorySourceProvider) sps
				.getSourceProvider(
						RepositorySourceProvider.REPOSITORY_PROPERTY);
		rsp.selectionChanged(null, selection);
