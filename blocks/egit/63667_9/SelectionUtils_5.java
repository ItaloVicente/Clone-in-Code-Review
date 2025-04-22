	public static Repository getCurrentRepository() {
		if (rsp == null) {
			ISourceProviderService sps = PlatformUI.getWorkbench()
					.getService(ISourceProviderService.class);
			if (sps == null) {
				return null;
			}
			rsp = (RepositorySourceProvider) sps.getSourceProvider(
					RepositorySourceProvider.REPOSITORY_PROPERTY);
		}
		if (rsp == null) {
			return null;
		}
		Repository repository = rsp.waitFor();
		return repository;
