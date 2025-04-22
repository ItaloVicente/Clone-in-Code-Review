    	if (configElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLASS) == null) {
            throw new CoreException(new Status(IStatus.ERROR, configElement
					.getContributor().getName(), 0,
                    "Invalid extension (Missing class name): " + getId(), //$NON-NLS-1$
                    null));
        }
    }
