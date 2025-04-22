	private static final String ATT_NAME = "name";//$NON-NLS-1$

	private static final String ATT_VALUE = "value";//$NON-NLS-1$

	public void addHelp(MarkerHelpRegistry registry) {
		IExtensionRegistry extensionRegistry = Platform.getExtensionRegistry();
		markerHelpRegistry = registry;
		readRegistry(extensionRegistry, IDEWorkbenchPlugin.IDE_WORKBENCH,
				IDEWorkbenchPlugin.PL_MARKER_HELP);
		readRegistry(extensionRegistry, IDEWorkbenchPlugin.IDE_WORKBENCH,
				IDEWorkbenchPlugin.PL_MARKER_RESOLUTION);
	}

	@Override
