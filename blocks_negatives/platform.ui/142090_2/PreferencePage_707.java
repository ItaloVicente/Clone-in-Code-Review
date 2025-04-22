        IPreferencePage {

    /**
     * Preference store, or <code>null</code>.
     */
    private IPreferenceStore preferenceStore;

    /**
     * Valid state for this page; <code>true</code> by default.
     *
     * @see #isValid
     */
    private boolean isValid = true;

    /**
     * Body of page.
     */
    private Control body;

    /**
