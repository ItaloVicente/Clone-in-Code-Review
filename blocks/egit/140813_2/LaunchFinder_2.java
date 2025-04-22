	private static final IDebugUIPluginFacade debugPluginFacade;

	static {
		if (hasDebugUiBundle()) {
			debugPluginFacade = new DebugUIPluginFacade();
		} else {
			debugPluginFacade = new NoopDebugUIPluginFacade();
		}
	}

	private static final boolean hasDebugUiBundle() {
		try {
			return Class.forName(
					"org.eclipse.debug.core.ILaunchConfiguration") != null; //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
