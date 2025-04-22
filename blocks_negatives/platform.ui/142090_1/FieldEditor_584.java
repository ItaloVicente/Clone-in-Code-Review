        if (isDefaultPresented) {
            preferenceStore.setToDefault(preferenceName);
        } else {
            doStore();
        }
    }

    /**
     * Set the GridData on button to be one that is spaced for the
     * current font.
     * @param button the button the data is being set on.
     */

    protected void setButtonLayoutData(Button button) {

        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL);

        GC gc = new GC(button);
        gc.setFont(button.getFont());
        FontMetrics fontMetrics = gc.getFontMetrics();
        gc.dispose();

        int widthHint = org.eclipse.jface.dialogs.Dialog
                .convertVerticalDLUsToPixels(fontMetrics,
                        IDialogConstants.BUTTON_WIDTH);
        data.widthHint = Math.max(widthHint, button.computeSize(SWT.DEFAULT,
                SWT.DEFAULT, true).x);
        button.setLayoutData(data);
    }

    /**
     * Set whether or not the controls in the field editor
     * are enabled.
     * @param enabled The enabled state.
     * @param parent The parent of the controls in the group.
     *  Used to create the controls if required.
     */
    public void setEnabled(boolean enabled, Composite parent) {
        getLabelControl(parent).setEnabled(enabled);
    }
