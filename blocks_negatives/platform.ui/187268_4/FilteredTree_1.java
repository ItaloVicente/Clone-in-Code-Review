	/**
	 * Get image descriptors for the clear button.
	 */
	static {
		Bundle bundle = FrameworkUtil.getBundle(FilteredTree.class);
		URL enabledURL = FileLocator.find(bundle, enabledPath, null);
		ImageDescriptor enabledDesc = ImageDescriptor.createFromURL(enabledURL);
		if (enabledDesc != null) {
			JFaceResources.getImageRegistry().put(CLEAR_ICON, enabledDesc);
			JFaceResources.getImageRegistry().put(PRESSED_CLEAR_ICON,
					ImageDescriptor.createWithFlags(enabledDesc, SWT.IMAGE_GRAY));
		}

		URL disabledURL = FileLocator.find(bundle, disabledPath, null);
		ImageDescriptor disabledDesc = ImageDescriptor.createFromURL(disabledURL);
		if (disabledDesc != null) {
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON, disabledDesc);
		}
	}

