
	private static final boolean hasDebugUiBundle() {
		try {
			return Class
					.forName("org.eclipse.debug.ui.StringVariableSelectionDialog") != null; //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
