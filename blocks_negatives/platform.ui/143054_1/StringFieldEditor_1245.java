    }

    /**
     * Sets the strategy for validating the text.
     * <p>
     * Calling this method has no effect after <code>createPartControl</code>
     * is called. Thus this method is really only useful for subclasses to call
     * in their constructor. However, it has public visibility for backward
     * compatibility.
     * </p>
     *
     * @param value either <code>VALIDATE_ON_KEY_STROKE</code> to perform
     *  on the fly checking (the default), or <code>VALIDATE_ON_FOCUS_LOST</code> to
     *  perform validation only after the text has been typed in
     */
    public void setValidateStrategy(int value) {
        Assert.isTrue(value == VALIDATE_ON_FOCUS_LOST
                || value == VALIDATE_ON_KEY_STROKE);
        validateStrategy = value;
    }

    /**
     * Shows the error message set via <code>setErrorMessage</code>.
     */
    public void showErrorMessage() {
        showErrorMessage(errorMessage);
    }

    /**
     * Informs this field editor's listener, if it has one, about a change
     * to the value (<code>VALUE</code> property) provided that the old and
     * new values are different.
     * <p>
     * This hook is <em>not</em> called when the text is initialized
     * (or reset to the default value) from the preference store.
     * </p>
     */
    protected void valueChanged() {
        setPresentsDefaultValue(false);
        boolean oldState = isValid;
        refreshValidState();

        if (isValid != oldState) {
