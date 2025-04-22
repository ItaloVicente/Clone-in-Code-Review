        }
    }



	/**
     * Apply the dialog font to the composite and it's children
     * if it is set. Subclasses may override if they wish to
     * set the font themselves.
     * @param composite
     */
    protected void applyDialogFont(Composite composite) {
        Dialog.applyDialogFont(composite);
    }

    /**
     * Creates and returns an SWT label under the given composite.
     *
     * @param parent the parent composite
     * @return the new label
     */
    protected Label createDescriptionLabel(Composite parent) {
        Label result = null;
        String description = getDescription();
        if (description != null) {
            result = new Label(parent, SWT.WRAP);
            result.setFont(parent.getFont());
            result.setText(description);
        }
        return result;
    }

    /**
     * Computes the size needed by this page's UI control.
     * <p>
     * All pages should override this method and set the appropriate sizes
     * of their widgets, and then call <code>super.doComputeSize</code>.
     * </p>
     *
     * @return the size of the preference page encoded as
     *   <code>new Point(width,height)</code>
     */
    protected Point doComputeSize() {
        if (descriptionLabel != null && body != null) {
            Point bodySize = body.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
            GridData gd = (GridData) descriptionLabel.getLayoutData();
            gd.widthHint = bodySize.x;
        }
        return getControl().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
    }

    /**
     * Returns the preference store of this preference page.
     * <p>
     * This is a framework hook method for subclasses to return a
     * page-specific preference store. The default implementation
     * returns <code>null</code>.
     * </p>
     *
     * @return the preference store, or <code>null</code> if none
     */
    protected IPreferenceStore doGetPreferenceStore() {
        return null;
    }

    /**
     * Returns the container of this page.
     *
     * @return the preference page container, or <code>null</code> if this
     *   page has yet to be added to a container
     */
    public IPreferencePageContainer getContainer() {
        return container;
    }

    /**
     * Returns the preference store of this preference page.
     *
     * @return the preference store , or <code>null</code> if none
     */
    public IPreferenceStore getPreferenceStore() {
        if (preferenceStore == null) {
