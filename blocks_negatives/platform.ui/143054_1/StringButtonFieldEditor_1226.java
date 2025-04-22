        ((GridData) getTextControl().getLayoutData()).horizontalSpan = numColumns - 2;
    }

    /**
     * Notifies that this field editor's change button has been pressed.
     * <p>
     * Subclasses must implement this method to provide a corresponding
     * new string for the text field. If the returned value is <code>null</code>,
     * the currently displayed value remains.
     * </p>
     *
     * @return the new string to display, or <code>null</code> to leave the
     *  old string showing
     */
    protected abstract String changePressed();

    @Override
