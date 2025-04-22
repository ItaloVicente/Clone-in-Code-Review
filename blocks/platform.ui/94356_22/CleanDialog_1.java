	private SearchPattern searchPattern = new SearchPattern();
	private Label clearLabel;

	private static final String CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEANDIALOG_CLEAR_ICON"; //$NON-NLS-1$

	private static final String DISABLED_CLEAR_ICON = "org.eclipse.ui.internal.dialogs.CLEANDIALOG_DCLEAR_ICON"; //$NON-NLS-1$

	static {
		ImageDescriptor descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
				"$nl$/icons/full/etool16/clear_co.png"); //$NON-NLS-1$
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(CLEAR_ICON, descriptor);
		}
		descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(PlatformUI.PLUGIN_ID,
				"$nl$/icons/full/dtool16/clear_co.png"); //$NON-NLS-1$
		if (descriptor != null) {
			JFaceResources.getImageRegistry().put(DISABLED_CLEAR_ICON, descriptor);
		}
	}
