    /**
     * Returns the preference store used for publicly settable workbench preferences.
     * Constants for these preferences are defined on
     * {@link org.eclipse.ui.IWorkbenchPreferenceConstants}.
     *
     * @return the workbench public preference store
     * @since 3.0
     */
    public static IPreferenceStore getPreferenceStore() {
        return PrefUtil.getAPIPreferenceStore();
    }
