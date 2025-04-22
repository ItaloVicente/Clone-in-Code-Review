    	IWorkingSetUpdater result = null;
        try {
            result = (IWorkingSetUpdater)WorkbenchPlugin.createExtension(configElement, ATT_UPDATER_CLASS);
        } catch (CoreException exception) {
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

    /**
     * Returns whether working sets based on this descriptor are editable.
     *
     * @return <code>true</code> if working sets based on this descriptor are editable; otherwise
     *  <code>false</code>
     *
     * @since 3.1
     */
    public boolean isEditable() {
        return getPageClassName() != null;
    }
