    protected void createEarlyStartupSelection(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(WorkbenchMessages.StartupPreferencePage_label);
        label.setFont(parent.getFont());
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(data);
