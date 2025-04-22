        selectionChanged(new SelectionChangedEvent(event.getViewer(), event
                .getViewer().getSelection()));
        getContainer().showPage(getNextPage());
    }

    /**
     * Layout the top control.
     *
     * @param control the control.
     * @since 3.0
     */
    private void layoutTopControl(Control control) {
        GridData data = new GridData(GridData.FILL_BOTH);

        int availableRows = DialogUtil.availableRows(control.getParent());

        if (availableRows > 50) {
            data.heightHint = SIZING_LISTS_HEIGHT;
        } else {
            data.heightHint = availableRows * 3;
        }

        control.setLayoutData(data);

    }

    /**
     * Uses the dialog store to restore widget values to the values that they
     * held last time this wizard was used to completion.
     */
    private void restoreWidgetValues() {

        IDialogSettings settings = getDialogSettings();
        if (settings == null) {
