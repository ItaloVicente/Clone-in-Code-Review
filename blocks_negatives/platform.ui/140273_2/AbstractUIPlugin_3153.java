    }

    /**
     * The <code>AbstractUIPlugin</code> implementation of this
     * <code>Plugin</code> method forwards to
     * <code>initializeDefaultPreferences(IPreferenceStore)</code>.
     * <p>
     * A subclass may reimplement this method to set default values for the core
     * runtime preference store in the standard way. This is the recommended way
     * to do this. The older
     * <code>initializeDefaultPreferences(IPreferenceStore)</code> method
     * serves a similar purpose. If this method is reimplemented, do not send
     * super, and do not override
     * <code>initializeDefaultPreferences(IPreferenceStore)</code>.
     * </p>
     *
     * @deprecated this is only called if the runtime compatibility layer is
     *             present. See the deprecated comment in
     *             {@link Plugin#initializeDefaultPluginPreferences}.
     *
     * @see #initializeDefaultPreferences
     * @since 2.0
     */
    @Deprecated
