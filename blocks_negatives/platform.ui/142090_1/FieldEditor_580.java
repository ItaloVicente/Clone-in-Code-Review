    	return null;
    }

    /**
     * Return the DialogPage that the receiver is sending
     * updates to.
     *
     * @return DialogPage or <code>null</code> if it
     * has not been set.
     *
     * @since 3.1
     */
    protected DialogPage getPage(){
    	return page;
    }

    /**
     * Returns the preference store used by this field editor.
     *
     * @return the preference store, or <code>null</code> if none
     * @see #setPreferenceStore
     */
    public IPreferenceStore getPreferenceStore() {
        return preferenceStore;
    }

    /**
     * Initialize the field editor with the given preference name and label.
     *
     * @param name the name of the preference this field editor works on
     * @param text the label text of the field editor
     */
    protected void init(String name, String text) {
        Assert.isNotNull(name);
        Assert.isNotNull(text);
        preferenceName = name;
        this.labelText = text;
    }

    /**
     * Returns whether this field editor contains a valid value.
     * <p>
     * The default implementation of this framework method
     * returns <code>true</code>. Subclasses wishing to perform
     * validation should override both this method and
     * <code>refreshValidState</code>.
     * </p>
     *
     * @return <code>true</code> if the field value is valid,
     *   and <code>false</code> if invalid
     * @see #refreshValidState()
     */
    public boolean isValid() {
        return true;
    }

    /**
     * Initializes this field editor with the preference value from
     * the preference store.
     */
    public void load() {
        if (preferenceStore != null) {
            isDefaultPresented = false;
            doLoad();
            refreshValidState();
        }
    }

    /**
     * Initializes this field editor with the default preference value
     * from the preference store.
     */
    public void loadDefault() {
        if (preferenceStore != null) {
            isDefaultPresented = true;
            doLoadDefault();
            refreshValidState();
        }
    }

    /**
     * Returns whether this field editor currently presents the
     * default value for its preference.
     *
     * @return <code>true</code> if the default value is presented,
     *   and <code>false</code> otherwise
     */
    public boolean presentsDefaultValue() {
        return isDefaultPresented;
    }

    /**
     * Refreshes this field editor's valid state after a value change
     * and fires an <code>IS_VALID</code> property change event if
     * warranted.
     * <p>
     * The default implementation of this framework method does
     * nothing. Subclasses wishing to perform validation should override
     * both this method and <code>isValid</code>.
     * </p>
     *
     * @see #isValid
     */
    protected void refreshValidState() {
    }

    /**
     * Sets the focus to this field editor.
     * <p>
     * The default implementation of this framework method
     * does nothing. Subclasses may reimplement.
     * </p>
     */
    public void setFocus() {
    }

    /**
     * Sets this field editor's label text.
     * The label is typically presented to the left of the entry field.
     *
     * @param text the label text
     */
    public void setLabelText(String text) {
        Assert.isNotNull(text);
        labelText = text;
        if (label != null) {
