
	@SuppressWarnings("restriction")
	private static class MappingListener implements
			org.eclipse.team.internal.core.IRepositoryProviderListener {

		@Override
		public void providerMapped(RepositoryProvider provider) {
			IProject project = provider.getProject();
			if (project != null) {
				ResourceUtil.markAsShared(project, provider.getID());
			}
		}

		@Override
		public void providerUnmapped(IProject project) {
			ResourceUtil.markAsUnshared(project);
		}

		public void register() {
			org.eclipse.team.internal.core.RepositoryProviderManager
					.getInstance().addListener(this);
		}

		public void dispose() {
			org.eclipse.team.internal.core.RepositoryProviderManager
					.getInstance().removeListener(this);
		}
	}
