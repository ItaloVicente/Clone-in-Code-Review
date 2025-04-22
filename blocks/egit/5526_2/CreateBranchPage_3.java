
	private IBranchNameProvider getBranchNameProvider() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(BRANCH_NAME_PROVIDER_ID);
		if (config.length > 0) {
			Object provider;
			try {
				provider = config[0].createExecutableExtension("class"); //$NON-NLS-1$
				if (provider instanceof IBranchNameProvider)
					return (IBranchNameProvider) provider;
			} catch (Throwable e) {
				Activator.logError("Failed to create branch name provider", e); //$NON-NLS-1$
			}
		}
		return null;
	}

	private String getBranchNameSuggestionFromProvider() {
		final AtomicReference<String> ref = new AtomicReference<String>();
		final IBranchNameProvider branchNameProvider = getBranchNameProvider();
		if (branchNameProvider != null)
			SafeRunner.run(new SafeRunnable() {
				public void run() throws Exception {
					ref.set(branchNameProvider.getBranchNameSuggestion());
				}
			});
		if (ref.get() != null)
			return ref.get();
		return null;
	}

