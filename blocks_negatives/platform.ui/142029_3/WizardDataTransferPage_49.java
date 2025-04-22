        		return super.getShellStyle() | SWT.SHEET;
        	}
        };

        return dialog.open() == 0;
    }

    /**
     * Restores control settings that were saved in the previous instance of this
     * page.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method does
     * nothing. Subclasses may override this hook method.
     * </p>
     */
    protected void restoreWidgetValues() {
    }

    /**
     * Saves control settings that are to be restored in the next instance of
     * this page.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method does
     * nothing. Subclasses may override this hook method.
     * </p>
     */
    protected void saveWidgetValues() {
    }

    /**
     * Determine if the page is complete and update the page appropriately.
     */
    protected void updatePageCompletion() {
        boolean pageComplete = determinePageCompletion();
        setPageComplete(pageComplete);
        if (pageComplete) {
            setErrorMessage(null);
        }
    }

    /**
     * Updates the enable state of this page's controls.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method does
     * nothing. Subclasses may extend this hook method.
     * </p>
     */
    protected void updateWidgetEnablements() {
    }

    /**
     * Returns whether this page's destination specification controls currently all
     * contain valid values.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method returns
     * <code>true</code>. Subclasses may reimplement this hook method.
     * </p>
     *
     * @return <code>true</code> indicating validity of all controls in the
     *   destination specification group
     */
    protected boolean validateDestinationGroup() {
        return true;
    }

    /**
     * Returns whether this page's options group's controls currently all contain
     * valid values.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method returns
     * <code>true</code>. Subclasses may reimplement this hook method.
     * </p>
     *
     * @return <code>true</code> indicating validity of all controls in the options
     *   group
     */
    protected boolean validateOptionsGroup() {
        return true;
    }

    /**
     * Returns whether this page's source specification controls currently all
     * contain valid values.
     * <p>
     * The <code>WizardDataTransferPage</code> implementation of this method returns
     * <code>true</code>. Subclasses may reimplement this hook method.
     * </p>
     *
     * @return <code>true</code> indicating validity of all controls in the
     *   source specification group
     */
    protected boolean validateSourceGroup() {
        return true;
    }

    /**
     *	Create the options specification widgets.
     *
     *	@param parent org.eclipse.swt.widgets.Composite
     */
    protected void createOptionsGroup(Composite parent) {
        Group optionsGroup = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        optionsGroup.setLayout(layout);
        optionsGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL));
        optionsGroup.setText(IDEWorkbenchMessages.WizardExportPage_options);
        optionsGroup.setFont(parent.getFont());

        createOptionsGroupButtons(optionsGroup);

    }

    /**
     * Display an error dialog with the specified message.
     *
     * @param message the error message
     */
    protected void displayErrorDialog(String message) {
        MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(),
                getErrorDialogTitle(), message, SWT.SHEET);
    }

    /**
     * Display an error dislog with the information from the
     * supplied exception.
     * @param exception Throwable
     */
    protected void displayErrorDialog(Throwable exception) {
        String message = exception.getMessage();
        if (message == null) {
