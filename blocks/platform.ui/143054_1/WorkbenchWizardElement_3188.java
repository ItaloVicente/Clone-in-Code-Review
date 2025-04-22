		return StructuredSelection.EMPTY;
	}

	public Object createExecutableExtension() throws CoreException {
		return WorkbenchPlugin.createExtension(configurationElement, IWorkbenchRegistryConstants.ATT_CLASS);
	}

