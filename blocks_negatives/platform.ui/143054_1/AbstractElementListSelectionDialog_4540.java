        Label label = super.createMessageArea(composite);

        GridData data = new GridData();
        data.grabExcessVerticalSpace = false;
        data.grabExcessHorizontalSpace = true;
        data.horizontalAlignment = GridData.FILL;
        data.verticalAlignment = GridData.BEGINNING;
        label.setLayoutData(data);

        fMessage = label;

        return label;
    }

    /**
     * Handles a selection changed event.
     * By default, the current selection is validated.
     */
    protected void handleSelectionChanged() {
        validateCurrentSelection();
    }

    /**
     * Validates the current selection and updates the status line
     * accordingly.
     * @return boolean <code>true</code> if the current selection is
     * valid.
     */
    protected boolean validateCurrentSelection() {
        Assert.isNotNull(fFilteredList);

        IStatus status;
        Object[] elements = getSelectedElements();

        if (elements.length > 0) {
            if (fValidator != null) {
                status = fValidator.validate(elements);
            } else {
                status = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null);
            }
        } else {
            if (fFilteredList.isEmpty()) {
                status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                        IStatus.ERROR, fEmptyListMessage, null);
            } else {
                status = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                        IStatus.ERROR, fEmptySelectionMessage, null);
            }
        }

        updateStatus(status);

        return status.isOK();
    }

    @Override
