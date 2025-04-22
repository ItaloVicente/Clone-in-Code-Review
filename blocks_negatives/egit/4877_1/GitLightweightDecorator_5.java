		if (resource.getType() == IResource.PROJECT) {
			try {
				decoratableResource = DecoratableResourceHelper
						.createTemporaryDecoratableResource(resource
								.getProject());
			} catch (IOException e) {
				handleException(
						resource,
						new CoreException(Activator.createErrorStatus(
								UIText.Decorator_exceptionMessage, e)));
				return;
			}
		}

		if (decoratableResource != null) {
			helper.decorate(decoration, decoratableResource);
		}

		GitDecoratorJob.getJobForRepository(
				mapping.getGitDirAbsolutePath().toString())
				.addDecorationRequest(element);
	}

	/**
	 * Process decoration requests for the given list of elements
	 *
	 * @param elements
	 *            the list of elements to be decorated
	 * @throws IOException
	 */
	static void processDecoration(final Object[] elements) throws IOException {
		final GitLightweightDecorator decorator = (GitLightweightDecorator) Activator
				.getDefault().getWorkbench().getDecoratorManager()
				.getBaseLabelProvider(DECORATOR_ID);
		if (decorator != null)
			decorator.prepareDecoration(elements);
		else
			throw new RuntimeException(
	}

	private void prepareDecoration(final Object[] elements) throws IOException {
		if (elements == null)
