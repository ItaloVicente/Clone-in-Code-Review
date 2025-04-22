        fFilters.add(filter);
    }

    /**
     * Sets an optional validator to check if the selection is valid. The
     * validator is invoked whenever the selection changes.
     *
     * @param validator
     *            the validator to validate the selection.
     */
    public void setValidator(ISelectionStatusValidator validator) {
        fValidator = validator;
    }

    /**
     * Sets the tree input.
     *
     * @param input
     *            the tree input.
     */
    public void setInput(Object input) {
        fInput = input;
    }

    /**
     * Expands elements in the tree.
     *
     * @param elements
     *            The elements that will be expanded.
     */
    public void setExpandedElements(Object[] elements) {
        fExpandedElements = elements;
    }

    /**
     * Sets the size of the tree in unit of characters.
     *
     * @param width
     *            the width of the tree.
     * @param height
     *            the height of the tree.
     */
    public void setSize(int width, int height) {
        fWidth = width;
        fHeight = height;
    }

    /**
     * Validate the receiver and update the status with the result.
     *
     */
    protected void updateOKStatus() {
        if (!fIsEmpty) {
            if (fValidator != null) {
                fCurrStatus = fValidator.validate(fViewer.getCheckedElements());
                updateStatus(fCurrStatus);
            } else if (!fCurrStatus.isOK()) {
                fCurrStatus = new Status(IStatus.OK, PlatformUI.PLUGIN_ID,
                        IStatus.OK, "", //$NON-NLS-1$
                        null);
            }
        } else {
            fCurrStatus = new Status(IStatus.ERROR, PlatformUI.PLUGIN_ID,
                    IStatus.OK, fEmptyListMessage, null);
        }
        updateStatus(fCurrStatus);
    }

    @Override
