    /**
     * Create a new IntroDescriptor for an extension.
     */
    public IntroDescriptor(IConfigurationElement configElement)
            throws CoreException {
    	element = configElement;

    	if (configElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS) == null) {
            throw new CoreException(new Status(IStatus.ERROR, configElement
					.getContributor().getName(), 0,
                    "Invalid extension (Missing class name): " + getId(), //$NON-NLS-1$
                    null));
        }
    }
