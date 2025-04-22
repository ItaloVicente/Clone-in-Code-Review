                editSelectedVariable();
            }
        });
        editButton.setFont(font);
        setButtonLayoutData(editButton);

        removeButton = new Button(groupComponent, SWT.PUSH);
        removeButton.setText(IDEWorkbenchMessages.PathVariablesBlock_removeVariableButton);
        removeButton.addSelectionListener(new SelectionAdapter() {
            @Override
