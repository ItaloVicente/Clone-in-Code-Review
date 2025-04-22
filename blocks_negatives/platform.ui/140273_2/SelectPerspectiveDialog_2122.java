    }

    /**
     * Returns the current selection.
     *
     * @return the current selection
     */
    public IPerspectiveDescriptor getSelection() {
        return perspDesc;
    }

    /**
     * Handle a double click event on the list
     */
    protected void handleDoubleClickEvent() {
        okPressed();
    }

    /**
     * Layout the top control.
     *
     * @param control the control.
     */
    private void layoutTopControl(Control control) {
        GridData spec = new GridData(GridData.FILL_BOTH);
        spec.widthHint = LIST_WIDTH;
        spec.heightHint = LIST_HEIGHT;
        control.setLayoutData(spec);
    }

    /**
     * Notifies that the selection has changed.
     *
     * @param event event object describing the change
     */
    @Override
