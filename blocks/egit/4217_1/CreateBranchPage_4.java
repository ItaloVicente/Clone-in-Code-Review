
	private IWorkflowProvider getWorkflowProvider() throws CoreException {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(IWorkflowProvider.WORKFLOW_PROVIDER_ID);
		if (config.length > 0) {
			Object provider;
			provider = config[0].createExecutableExtension("class");//$NON-NLS-1$
			if (provider instanceof IWorkflowProvider) {
				return (IWorkflowProvider) provider;
			} else {
				Activator.logError(
						UIText.CommitDialog_WrongTypeOfCommitMessageProvider,
						null);
			}
		}
		return null;
	}

	private String getBranchNameSuggestion() {
		IWorkflowProvider workflowProvider;

		try {
			workflowProvider = getWorkflowProvider();
			if (workflowProvider != null) {
				return workflowProvider.getBranchNameSuggestion();
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
