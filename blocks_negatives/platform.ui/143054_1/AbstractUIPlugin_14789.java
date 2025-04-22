        }
        return preferenceStore;
    }

    /**
     * Returns the Platform UI workbench.
     * <p>
     * This method exists as a convenience for plugin implementors.  The
     * workbench can also be accessed by invoking <code>PlatformUI.getWorkbench()</code>.
     * </p>
     * @return IWorkbench the workbench for this plug-in
     */
    public IWorkbench getWorkbench() {
        return PlatformUI.getWorkbench();
    }

    /**
     * Initializes a preference store with default preference values
     * for this plug-in.
     * <p>
     * This method is called after the preference store is initially loaded
     * (default values are never stored in preference stores).
     * </p>
     * <p>
     * The default implementation of this method does nothing.
     * Subclasses should reimplement this method if the plug-in has any preferences.
     * </p>
     * <p>
     * A subclass may reimplement this method to set default values for the
     * preference store using JFace API. This is the older way of initializing
     * default values. If this method is reimplemented, do not override
     * <code>initializeDefaultPluginPreferences()</code>.
     * </p>
     *
     * @param store the preference store to fill
     *
     * @deprecated this is only called if the runtime compatibility layer is
     *             present. See {@link #initializeDefaultPluginPreferences}.
     */
    @Deprecated
