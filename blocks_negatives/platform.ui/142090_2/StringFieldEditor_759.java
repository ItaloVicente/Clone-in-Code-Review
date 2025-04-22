        if (textField != null) {
            textField.setFocus();
        }
    }

    /**
     * Sets this field editor's value.
     *
     * @param value the new value, or <code>null</code> meaning the empty string
     */
    public void setStringValue(String value) {
        if (textField != null) {
            if (value == null) {
