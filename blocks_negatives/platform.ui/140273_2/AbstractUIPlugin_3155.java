    /**
     * Loads the preference store for this plug-in.
     * The default implementation looks for a standard named file in the
     * plug-in's read/write state area. If no file is found or a problem
     * occurs, a new empty preference store is silently created.
     * <p>
     * This framework method may be overridden, although this is typically
     * unnecessary.
     * </p>
     *
     * @deprecated As of Eclipse 2.0, a basic preference store exists for all
     * plug-ins. This method now exists only for backwards compatibility.
     * It is called as the plug-in's preference store is being initialized.
     * The plug-ins preferences are loaded from the file regardless of what
     * this method does.
     */
    @Deprecated
