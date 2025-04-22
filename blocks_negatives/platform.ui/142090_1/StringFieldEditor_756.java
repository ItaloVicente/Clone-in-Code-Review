        return getPreferenceStore().getString(getPreferenceName());
    }

    /**
     * Returns this field editor's text control.
     *
     * @return the text control, or <code>null</code> if no
     * text field is created yet
     */
    protected Text getTextControl() {
        return textField;
    }

    /**
     * Returns this field editor's text control.
     * <p>
     * The control is created if it does not yet exist
     * </p>
     *
     * @param parent the parent
     * @return the text control
     */
    public Text getTextControl(Composite parent) {
        if (textField == null) {
            textField = new Text(parent, SWT.SINGLE | SWT.BORDER);
            textField.setFont(parent.getFont());
            switch (validateStrategy) {
            case VALIDATE_ON_KEY_STROKE:
                textField.addKeyListener(new KeyAdapter() {

                    @Override
