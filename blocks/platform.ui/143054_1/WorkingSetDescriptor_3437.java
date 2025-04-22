		IWorkingSetUpdater result = null;
		try {
			result = (IWorkingSetUpdater) WorkbenchPlugin.createExtension(configElement, ATT_UPDATER_CLASS);
		} catch (CoreException exception) {
			WorkbenchPlugin.log("Unable to create working set updater: " + //$NON-NLS-1$
					updaterClassName, exception.getStatus());
		}
		return result;
	}

	public boolean isUpdaterClassLoaded() {
		return WorkbenchPlugin.isBundleLoadedForExecutableExtension(configElement, ATT_UPDATER_CLASS);
	}

	public boolean isElementAdapterClassLoaded() {
		return WorkbenchPlugin.isBundleLoadedForExecutableExtension(configElement, ATT_ELEMENT_ADAPTER_CLASS);
	}

	public boolean isEditable() {
		return getPageClassName() != null;
	}
