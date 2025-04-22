        overwriteExistingResourcesCheckbox = new Button(optionsGroup, SWT.CHECK);
        overwriteExistingResourcesCheckbox.setFont(optionsGroup.getFont());
        overwriteExistingResourcesCheckbox.setText(DataTransferMessages.FileImport_overwriteExisting);

        createTopLevelFolderCheckbox= new Button(optionsGroup, SWT.CHECK);
        createTopLevelFolderCheckbox.setFont(optionsGroup.getFont());
        createTopLevelFolderCheckbox.setText(DataTransferMessages.FileImport_createTopLevel);
        createTopLevelFolderCheckbox.setSelection(false);
        createTopLevelFolderCheckbox.addSelectionListener(new SelectionAdapter() {
        	@Override
