		setupImageRegistry();
	}

	private void setupImageRegistry() {
		getImageRegistry().put(ICON_REPOSITORY, imageDescriptorFromPlugin(getPluginId(), "icons/obj16/repository_rep.gif")); //$NON-NLS-1$
		getImageRegistry().put(ICON_BRANCHES, imageDescriptorFromPlugin(getPluginId(), "icons/obj16/branches_rep.gif")); //$NON-NLS-1$
		getImageRegistry().put(ICON_CHECKEDOUT_OVR, imageDescriptorFromPlugin(getPluginId(), "icons/ovr/checkedout_ov.gif")); //$NON-NLS-1$
