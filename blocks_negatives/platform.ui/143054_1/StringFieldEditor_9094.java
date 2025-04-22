        GridData gd = (GridData) textField.getLayoutData();
        gd.horizontalSpan = numColumns - 1;
        gd.grabExcessHorizontalSpace = gd.horizontalSpan == 1;
    }

    /**
     * Checks whether the text input field contains a valid value or not.
     *
     * @return <code>true</code> if the field value is valid,
     *   and <code>false</code> if invalid
     */
    protected boolean checkState() {
        boolean result = false;
        if (emptyStringAllowed) {
