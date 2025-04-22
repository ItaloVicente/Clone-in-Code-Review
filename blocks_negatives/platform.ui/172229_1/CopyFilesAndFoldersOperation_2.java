		else {
			if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_PROMPT)) {
				ImportTypeDialog dialog= new ImportTypeDialog(messageShell, dropOperation, fileNames, destination);
				dialog.setResource(destination);
				if (dialog.open() == Window.OK) {
					mode= dialog.getSelection();
					variable= dialog.getVariable();
				}
			} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_MOVE_COPY)) {
				mode= ImportTypeDialog.IMPORT_COPY;
			} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_LINK)) {
				mode= ImportTypeDialog.IMPORT_LINK;
			} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_LINK_AND_VIRTUAL_FOLDER)) {
				mode= ImportTypeDialog.IMPORT_VIRTUAL_FOLDERS_AND_LINKS;
