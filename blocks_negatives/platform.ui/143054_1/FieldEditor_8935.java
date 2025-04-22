     * Sets the preference store used by this field editor.
     *
     * @param store the preference store, or <code>null</code> if none
     * @see #getPreferenceStore
     */
    public void setPreferenceStore(IPreferenceStore store) {
        preferenceStore = store;
    }

    /**
     * Sets whether this field editor is presenting the default value.
     *
     * @param booleanValue <code>true</code> if the default value is being presented,
     *  and <code>false</code> otherwise
     */
    protected void setPresentsDefaultValue(boolean booleanValue) {
        isDefaultPresented = booleanValue;
    }

    /**
     * Sets or removes the property change listener for this field editor.
     * <p>
     * Note that field editors can support only a single listener.
     * </p>
     *
     * @param listener a property change listener, or <code>null</code>
     *  to remove
     */
    public void setPropertyChangeListener(IPropertyChangeListener listener) {
        propertyChangeListener = listener;
    }

    /**
     * Shows the given error message in the page for this
     * field editor if it has one.
     *
     * @param msg the error message
     */
    protected void showErrorMessage(String msg) {
        if (page != null) {
