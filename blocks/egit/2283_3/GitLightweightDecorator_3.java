		} catch (CoreException e) {
			handleException(resource, e);
			return;
		}

		if (Team.isIgnoredHint(resource))
			return;

		final RepositoryMapping mapping = RepositoryMapping
				.getMapping(resource);
		if (mapping == null)
			return;

		if (mapping.getRepoRelativePath(resource) == null)
			return;

		GitDecoratorJob.getJobForRepository(
				mapping.getGitDirAbsolutePath().toString())
				.addDecorationRequest(element);
	}

	static void processDecoration(final Object[] elements) {
		final GitLightweightDecorator decorator = (GitLightweightDecorator) Activator
				.getDefault().getWorkbench().getDecoratorManager()
				.getBaseLabelProvider(DECORATOR_ID);
		if (decorator != null)
			decorator.prepareDecoration(elements);
		else
			exceptions
					.handleException(new CoreException(
							Activator
									.createErrorStatus(UIText.GitLightweightDecorator_AsynchronousDecorationError)));
	}

	private void prepareDecoration(final Object[] elements) {
		if (elements == null)
			return;
