    /**
     * Create the group that shows the user defined entries for the dialog.
     *
     * @param parent
     *            the parent this is being created in.
     */
    private void createUserEntryGroup(Composite parent) {
        Font font = parent.getFont();
        Composite userDefinedGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        userDefinedGroup.setLayout(layout);
        userDefinedGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
        Label fTitle = new Label(userDefinedGroup, SWT.NONE);
        fTitle.setFont(font);
        fTitle.setText(filterTitle);
        userDefinedText = new Text(userDefinedGroup, SWT.SINGLE | SWT.BORDER);
        userDefinedText.setFont(font);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL);
        userDefinedText.setLayoutData(data);
    }
