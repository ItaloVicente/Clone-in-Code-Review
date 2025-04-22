	public StickyViewDescriptor(IConfigurationElement element) throws CoreException {
		this.configurationElement = element;
		id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
		if (id == null) {
			throw new CoreException(new Status(IStatus.ERROR, element.getContributor().getName(), 0,
					"Invalid extension (missing id) ", null));//$NON-NLS-1$
