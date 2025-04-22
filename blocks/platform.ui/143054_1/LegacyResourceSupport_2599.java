	public static Class getResourceClass() {
		if (iresourceClass != null) {
			return iresourceClass;
		}
		Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.IResource"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			iresourceClass = c;
		}
		return c;
	}
