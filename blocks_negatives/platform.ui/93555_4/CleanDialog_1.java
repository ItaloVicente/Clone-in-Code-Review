        allButton = new Button(area, SWT.RADIO);
        allButton.setText(IDEWorkbenchMessages.CleanDialog_cleanAllButton);
        allButton.setSelection(!selectSelectedButton);
        allButton.addSelectionListener(updateEnablement);
        selectedButton = new Button(area, SWT.RADIO);
        selectedButton.setText(IDEWorkbenchMessages.CleanDialog_cleanSelectedButton);
        selectedButton.setSelection(selectSelectedButton);
        selectedButton.addSelectionListener(updateEnablement);

        createProjectSelectionTable(area);

