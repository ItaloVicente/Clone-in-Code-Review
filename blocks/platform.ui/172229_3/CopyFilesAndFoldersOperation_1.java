		else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_PROMPT)) {
			ImportTypeDialog dialog= new ImportTypeDialog(messageShell, dropOperation, fileNames, destination);
			dialog.setResource(destination);
			if (dialog.open() == Window.OK) {
				mode= dialog.getSelection();
				variable= dialog.getVariable();
