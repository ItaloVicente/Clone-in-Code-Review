        
        Dialog.applyDialogFont(composite);
        
        return composite;
    }

    /**
     * @param composite
     */
    private void createPromptButton(Composite composite) {
        activityPromptButton = new Button(composite, SWT.CHECK);
        activityPromptButton.setText(strings.getProperty(ACTIVITY_PROMPT_BUTTON, ActivityMessages.activityPromptButton));
        activityPromptButton.setToolTipText(strings.getProperty(ACTIVITY_PROMPT_BUTTON_TOOLTIP, ActivityMessages.activityPromptToolTip));
        GridData data = new GridData();
        data.horizontalSpan = 2;
        activityPromptButton.setLayoutData(data);
        activityPromptButton.setSelection(getPreferenceStore()
                .getBoolean(
                        IPreferenceConstants.SHOULD_PROMPT_FOR_ENABLEMENT));
    }

    private void createButtons(final Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(4, false);
        layout.marginHeight = layout.marginWidth = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.horizontalSpan = 2;
        composite.setLayoutData(data);

        Button enableAll = new Button(composite, SWT.PUSH);
        enableAll.addSelectionListener(new SelectionAdapter() {

            @Override
