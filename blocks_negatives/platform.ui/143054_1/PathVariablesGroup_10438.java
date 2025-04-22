            String varName = (String) selectedItem.getData();
            if (isBuiltInVariable(varName))
                return false;
        }
        return true;
    }

    /**
     * @param varName
     *            the variable name to test
     */
    private boolean isBuiltInVariable(String varName) {
        if (currentResource != null) {
        	return !pathVariableManager.isUserDefined(varName);
        }
        return false;
    }

    /**
     * Sets the <code>GridData</code> on the specified button to
     * be one that is spaced for the current dialog page units. The
     * method <code>initializeDialogUnits</code> must be called once
     * before calling this method for the first time.
     *
     * @param button the button to set the <code>GridData</code>
     * @return the <code>GridData</code> set on the specified button
     */
    private GridData setButtonLayoutData(Button button) {
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
        int widthHint = Dialog.convertHorizontalDLUsToPixels(fontMetrics,
                IDialogConstants.BUTTON_WIDTH);
        data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
                SWT.DEFAULT, true).x);
        button.setLayoutData(data);
        return data;
    }

    /**
     * Sets the enabled state of the group's widgets.
     * Does nothing if called prior to calling <code>createContents</code>.
     *
     * @param enabled the new enabled state of the group's widgets
     */
    public void setEnabled(boolean enabled) {
        if (variableTable != null && !variableTable.getTable().isDisposed()) {
            variableLabel.setEnabled(enabled);
            variableTable.getTable().setEnabled(enabled);
            addButton.setEnabled(enabled);
            if (enabled) {
