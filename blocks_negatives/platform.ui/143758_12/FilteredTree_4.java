	/**
	 * Get image descriptors for the clear button.
	 */
	static {
		Bundle bundle = org.eclipse.e4.ui.internal.workbench.swt.WorkbenchSWTActivator
				.getDefault().getBundle();
		IPath enabledPath = new Path("$nl$/icons/full/etool16/clear_co.png");
		URL enabledURL = FileLocator.find(bundle, enabledPath, null);
		ImageDescriptor enabledDesc = ImageDescriptor.createFromURL(enabledURL);
		if (enabledDesc != null)
			JFaceResources.getImageRegistry().put(CLEAR_ICON, enabledDesc);

		IPath disabledPath = new Path("$nl$/icons/full/etool16/clear_co.png");
		URL disabledURL = FileLocator.find(bundle, disabledPath, null);
		ImageDescriptor disabledDesc = ImageDescriptor
				.createFromURL(disabledURL);
		if (disabledDesc != null)
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON,
					disabledDesc);
	}

