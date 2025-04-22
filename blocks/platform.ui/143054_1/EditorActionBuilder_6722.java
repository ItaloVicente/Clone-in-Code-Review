	public IEditorActionBarContributor readActionExtensions(IEditorDescriptor desc) {
		ExternalContributor ext = null;
		readContributions(desc.getId(), TAG_CONTRIBUTION_TYPE, IWorkbenchRegistryConstants.PL_EDITOR_ACTIONS);
		if (cache != null) {
			ext = new ExternalContributor(cache);
			cache = null;
		}
		return ext;
	}
