		} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_MOVE_COPY)) {
			mode= ImportTypeDialog.IMPORT_COPY;
		} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_LINK)) {
			mode= ImportTypeDialog.IMPORT_LINK;
		} else if (dndPreference.equals(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_LINK_AND_VIRTUAL_FOLDER)) {
			mode= ImportTypeDialog.IMPORT_VIRTUAL_FOLDERS_AND_LINKS;
