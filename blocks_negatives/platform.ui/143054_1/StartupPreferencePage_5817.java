    protected void createEarlyStartupSelection(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(WorkbenchMessages.StartupPreferencePage_label);
        label.setFont(parent.getFont());
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        label.setLayoutData(data);
        pluginsList = new Table(parent, SWT.BORDER | SWT.CHECK | SWT.H_SCROLL
                | SWT.V_SCROLL);
        data = new GridData(GridData.FILL_BOTH);
        pluginsList.setFont(parent.getFont());
        pluginsList.setLayoutData(data);
