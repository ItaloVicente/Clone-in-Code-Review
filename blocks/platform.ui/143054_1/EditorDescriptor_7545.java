		}

		return null;
	}

	public IEditorActionBarContributor createActionBarContributor() {
		if (configurationElement == null) {
			return null;
		}

		String className = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
		if (className == null) {
