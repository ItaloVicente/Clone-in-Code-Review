		@SuppressWarnings("resource")
		Repository currentRepository = repositoryRef == null ? null
				: repositoryRef.get();
		if (currentRepository == null && repositoryRef != null) {
			repositoryRef = null;
			if (newRepository == null) {
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
				return;
			}
		}
		if (currentRepository != newRepository) {
			if (newRepository != null) {
				repositoryRef = new WeakReference<>(newRepository);
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY,
						newRepository.getDirectory().getAbsolutePath());
			} else {
				repositoryRef = null;
				fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
						REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
			}
