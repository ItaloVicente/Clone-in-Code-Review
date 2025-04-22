        parent.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        createButton(parent, DETAILS_ID, WorkbenchMessages.AboutDialog_DetailsButton, false); 

        Label l = new Label(parent, SWT.NONE);
        l.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout layout = (GridLayout) parent.getLayout();
        layout.numColumns++;
        layout.makeColumnsEqualWidth = false;

        Button b = createButton(parent, IDialogConstants.OK_ID,
                IDialogConstants.OK_LABEL, true);
        b.setFocus();
    }

    /**
     * Creates and returns the contents of the upper part 
     * of the dialog (above the button bar).
     *
     * Subclasses should overide.
     *
     * @param parent  the parent composite to contain the dialog area
     * @return the dialog area control
     */
    @Override
