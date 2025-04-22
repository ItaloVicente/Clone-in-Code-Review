	private static Class iresourceClass = null;

	private static Class ifileClass;

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

	public static Class getResourceMappingClass() {
		if (resourceMappingClass != null) {
			return resourceMappingClass;
		}
		Class c = loadClass("org.eclipse.core.resources", "org.eclipse.core.resources.mapping.ResourceMapping"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			resourceMappingClass = c;
		}
		return c;
	}

	public static Class getIContributorResourceAdapterClass() {
		if (icontributorResourceAdapterClass != null) {
			return icontributorResourceAdapterClass;
		}
		Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.IContributorResourceAdapter"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			icontributorResourceAdapterClass = c;
		}
		return c;
	}

	public static Class getIContributorResourceAdapter2Class() {
		if (icontributorResourceAdapter2Class != null) {
			return icontributorResourceAdapter2Class;
		}
		Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.ide.IContributorResourceAdapter2"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			icontributorResourceAdapter2Class = c;
		}
		return c;
	}

	private static Class loadClass(String bundleName, String className) {
		if (!resourceAdapterPossible) {
			return null;
		}
		Bundle bundle = Platform.getBundle(bundleName);
		if (bundle == null) {
			resourceAdapterPossible = false;
			return null;
		}
		if (!BundleUtility.isActivated(bundle)) {
			resourceAdapterPossible = true;
			return null;
		}
		try {
			return bundle.loadClass(className);
		} catch (ClassNotFoundException e) {
			resourceAdapterPossible = false;
			return null;
		}
	}

	public static Class getDefaultContributorResourceAdapterClass() {
		if (defaultContributorResourceAdapterClass != null) {
			return defaultContributorResourceAdapterClass;
		}
		Class c = loadClass("org.eclipse.ui.ide", "org.eclipse.ui.internal.ide.DefaultContributorResourceAdapter"); //$NON-NLS-1$ //$NON-NLS-2$
		if (c != null) {
			defaultContributorResourceAdapterClass = c;
		}
		return c;
	}
