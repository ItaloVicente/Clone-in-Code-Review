	private static final IDebugUIPlugin debugPlugin;

	static {
		if (hasDebugUiBundle())
			debugPlugin = new DebugUIPlugin();
		else
			debugPlugin = new NoDebugUIPlugin();
	}

	private static final boolean hasDebugUiBundle() {
		try {
			return Class.forName(
					"org.eclipse.debug.core.ILaunchConfiguration") != null; //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
