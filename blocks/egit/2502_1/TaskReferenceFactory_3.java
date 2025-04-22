	private AbstractTaskReference runResolverExtension(final IPath repoRoot,
			final String message, final String commitSHA) {

		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(RESOLVER_ID);
		final AbstractTaskReference[] result = new AbstractTaskReference[1];

		try {
			for (IConfigurationElement e : config) {
				final Object o = e.createExecutableExtension("class"); //$NON-NLS-1$
				if (o instanceof ITaskRepositoryResolver)
					SafeRunner.run(new ISafeRunnable() {
						public void handleException(Throwable exception) {
						}

						public void run() throws Exception {
							result[0] = ((ITaskRepositoryResolver) o).createTaskRerference(
									repoRoot, message, commitSHA);
						}
					});

				if (result[0] != null)
					break;
			}
		} catch (CoreException ex) {
			EGitMylynUI.getDefault().getLog().log(
					new Status(IStatus.ERROR, EGitMylynUI.PLUGIN_ID, "Failed extension call", ex)); //$NON-NLS-1$
		}
		return result[0];
	}
