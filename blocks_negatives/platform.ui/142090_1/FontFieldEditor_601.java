    }

    /**
     * Returns the change button for this field editor.
     *
     * @param parent The Composite to create the button in if required.
     * @return the change button
     */
    protected Button getChangeControl(Composite parent) {
        if (changeFontButton == null) {
            changeFontButton = new Button(parent, SWT.PUSH);
            if (changeButtonText != null) {
