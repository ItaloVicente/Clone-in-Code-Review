	private Label clearLabel;

	/**
	 * Image descriptor for enabled clear button.
	 */

	/**
	 * Image descriptor for disabled clear button.
	 */

	/**
	 * Get image descriptors for the clear button.
	 */
	static {
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(CLEAR_ICON, descriptor);
		}
		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON, descriptor);
		}
	}
