		GitDecoratorJob.getJobForRepository(
				mapping.getGitDirAbsolutePath().toString())
				.addDecorationRequest(element);
	}

	public static void processDecoration(final Object[] elements) {
		final GitLightweightDecorator decorator = (GitLightweightDecorator) Activator
				.getDefault().getWorkbench().getDecoratorManager()
				.getBaseLabelProvider(DECORATOR_ID);
		if (decorator != null)
			decorator.prepareDecoration(elements);
	}

	public void prepareDecoration(final Object[] elements) {
		if (elements == null)
			return;

		final IResource[] resources = new IResource[elements.length];
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null)
				resources[i] = getResource(elements[i]);
