    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.core.resources.IResource")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.0
     */
    private static Class iresourceClass = null;

    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.core.resources.IFile")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.1
     */
    private static Class ifileClass;

    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.ui.IContributorResourceAdapter")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.0
     */
    private static Class icontributorResourceAdapterClass = null;

    /**
     * Cached value of </code> org.eclipse.ui.IContributorResourceAdapter.getAdaptedResource(IAdaptable) </code>
     * <code>null</code> if not initialized or not present.
     *
     * @since 3.3
     */
    private static Method getAdaptedResourceMethod = null;

    /**
     * Cached value of </code> org.eclipse.ui.IContributorResourceAdapter2.getAdaptedResourceMapping(IAdaptable) </code>
     * <code>null</code> if not initialized or not present.
     *
     * @since 3.3
     */
    private static Method getAdaptedResourceMappingMethod = null;

    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.ui.ide.IContributorResourceAdapter2")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.1
     */
    private static Class icontributorResourceAdapter2Class = null;

    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.ui.internal.ide.DefaultContributorResourceAdapter")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.0
     */
    private static Class defaultContributorResourceAdapterClass = null;

    /**
     * Cached value for reflective result of <code>DefaultContributorRessourceAdapter.getDefault()</code>.
     * <code>null</code> if not initialized or not present.
     *
     * @since 3.3
     */
    private static Object defaultContributorResourceAdapter = null;

    /**
     * Cached value of
     * <code>Class.forName("org.eclipse.core.resources.mapping.ResourceMappingr")</code>;
     * <code>null</code> if not initialized or not present.
     * @since 3.0
     */
    private static Class resourceMappingClass = null;

    /**
     * Indicates whether the IDE plug-in (which supplies the
     * resource contribution adapters) is even around.
     */
    private static boolean resourceAdapterPossible = true;


    /**
     * Returns <code>IFile.class</code> or <code>null</code> if the
     * class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the resources plug-in.
     * </p>
     *
     * @return <code>IFile.class</code> or <code>null</code> if class
     * not available
     * @since 3.1
     */
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

    /**
     * Returns <code>IResource.class</code> or <code>null</code> if the
     * class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the resources plug-in.
     * </p>
     *
     * @return <code>IResource.class</code> or <code>null</code> if class
     * not available
     * @since 3.0
     */
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

    /**
     * Returns <code>ResourceMapping.class</code> or <code>null</code> if the
     * class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the resources plug-in.
     * </p>
     *
     * @return <code>ResourceMapping.class</code> or <code>null</code> if class
     * not available
     * @since 3.1
     */
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

    /**
     * Returns <code>IContributorResourceAdapter.class</code> or
     * <code>null</code> if the class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the IDE plug-in.
     * </p>
     *
     * @return <code>IContributorResourceAdapter.class</code> or
     * <code>null</code> if class not available
     * @since 3.0
     */
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

    /**
     * Returns <code>IContributorResourceAdapter2.class</code> or
     * <code>null</code> if the class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the IDE plug-in.
     * </p>
     *
     * @return <code>IContributorResourceAdapter.class</code> or
     * <code>null</code> if class not available
     * @since 3.1
     */
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

    /**
     * Returns <code>DefaultContributorResourceAdapter.class</code> or
     * <code>null</code> if the class is not available.
     * <p>
     * This method exists to avoid explicit references from the generic
     * workbench to the IDE plug-in.
     * </p>
     *
     * @return <code>DefaultContributorResourceAdapter.class</code> or
     * <code>null</code> if class not available
     * @since 3.0
     */
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

    private static Object getDefaultContributorResourceAdapter() {
        if (defaultContributorResourceAdapter != null) {
            return defaultContributorResourceAdapter;
        }
