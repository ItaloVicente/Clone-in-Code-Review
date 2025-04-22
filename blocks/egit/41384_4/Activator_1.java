	private static final boolean hasJavaPlugin() {
		try {
			return Class.forName("org.eclipse.jdt.internal.ui.JavaPlugin") != null; //$NON-NLS-1$
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
