        super.createButtonsForButtonBar(parent);
        createDetailsButton(parent);
    }

    /**
     * Create a spacer label to get the layout to not bunch the widgets.
     *
     * @param parent
     *            The parent of the new button.
     */
    protected void createSpacer(Composite parent) {
        Label spacer = new Label(parent, SWT.NONE);
        spacer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL
                | GridData.GRAB_HORIZONTAL));
    }

    /**
     * Create the details button for the receiver.
     *
     * @param parent
     *            The parent of the new button.
     */
    protected void createDetailsButton(Composite parent) {
        detailsButton = createButton(parent, IDialogConstants.DETAILS_ID,
                ProgressMessages.ProgressMonitorJobsDialog_DetailsTitle,
                false);
        detailsButton.addSelectionListener(widgetSelectedAdapter(e -> handleDetailsButtonSelect()));
        detailsButton.setCursor(arrowCursor);
        detailsButton.setEnabled(enableDetailsButton);
    }

    @Override
