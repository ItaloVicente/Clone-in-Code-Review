        if ((variableType & IResource.FOLDER) != 0) {
        	layout.numColumns++;
	        folderButton = new Button(buttonsComposite, SWT.PUSH);
	        folderButton.setText(IDEWorkbenchMessages.PathVariableDialog_folder);
	        folderButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false,
	        		false));
