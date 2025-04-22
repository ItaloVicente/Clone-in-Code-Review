		@SuppressWarnings("resource")
		Repository repository = repositoryRef == null ? null
				: repositoryRef.get();
		if (repository == null) {
			return Collections.singletonMap(REPOSITORY_PROPERTY, ""); //$NON-NLS-1$
		}
		return Collections.singletonMap(REPOSITORY_PROPERTY,
				repository.getDirectory().getAbsolutePath());
