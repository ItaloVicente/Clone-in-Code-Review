            oldValue = textField.getText();
            if (!oldValue.equals(value)) {
                textField.setText(value);
                valueChanged();
            }
        }
    }

    /**
     * Sets this text field's text limit.
     *
     * @param limit the limit on the number of character in the text
     *  input field, or <code>UNLIMITED</code> for no limit

     */
    public void setTextLimit(int limit) {
        textLimit = limit;
        if (textField != null) {
