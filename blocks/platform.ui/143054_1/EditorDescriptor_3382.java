		IEditorActionBarContributor contributor = null;
		try {
			contributor = (IEditorActionBarContributor) WorkbenchPlugin.createExtension(configurationElement,
					IWorkbenchRegistryConstants.ATT_CONTRIBUTOR_CLASS);
		} catch (CoreException e) {
			WorkbenchPlugin.log("Unable to create editor contributor: " + //$NON-NLS-1$
					id, e.getStatus());
		}
		return contributor;
	}

	public String getClassName() {
		if (configurationElement == null) {
			return className;
		}
		return RegistryReader.getClassValue(configurationElement, IWorkbenchRegistryConstants.ATT_CLASS);
	}

	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

	public IEditorPart createEditor() throws CoreException {
		Object extension = WorkbenchPlugin.createExtension(getConfigurationElement(),
				IWorkbenchRegistryConstants.ATT_CLASS);
		return ((InterceptContributions) Tweaklets.get(InterceptContributions.KEY)).tweakEditor(extension);
	}

	public String getFileName() {
		if (program == null) {
			if (configurationElement == null) {
				return fileName;
			}
			return configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_COMMAND);
		}
		return program.getName();
	}

	@Override
