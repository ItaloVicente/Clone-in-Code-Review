	private static Class icontributorResourceAdapterClass = null;

	private static Method getAdaptedResourceMethod = null;

	private static Method getAdaptedResourceMappingMethod = null;

	private static Class icontributorResourceAdapter2Class = null;

	private static Class defaultContributorResourceAdapterClass = null;

	private static Object defaultContributorResourceAdapter = null;

	private static Class resourceMappingClass = null;

	private static boolean resourceAdapterPossible = true;

	public static Class getFileClass() {
		if (ifileClass != null) {
			return ifileClass;
		}
		Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.IFile"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			ifileClass = c;
		}
		return c;
	}
