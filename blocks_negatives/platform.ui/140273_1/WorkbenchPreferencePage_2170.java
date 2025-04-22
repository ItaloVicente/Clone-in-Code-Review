        selectOnHoverButton.setLayoutData(data);

        label = WorkbenchMessages.WorkbenchPreference_singleClick_OpenAfterDelay;
        openAfterDelayButton = new Button(buttonComposite, SWT.CHECK | SWT.LEFT);
        openAfterDelayButton.setText(label);
        openAfterDelayButton.setEnabled(openOnSingleClick);
        openAfterDelayButton.setSelection(openAfterDelay);
        openAfterDelayButton.addSelectionListener(widgetSelectedAdapter(e -> openAfterDelay = openAfterDelayButton.getSelection()));
        data = new GridData();
