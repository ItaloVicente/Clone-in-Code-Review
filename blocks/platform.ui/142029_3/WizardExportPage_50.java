			if (exportSpecifiedTypesRadio.getSelection()) {
				selectedTypesNames = addToHistory(selectedTypesNames, typesToExportField.getText());
			}

			settings.put(STORE_SELECTED_TYPES_ID + pageName, selectedTypesNames);

			settings.put(STORE_EXPORT_ALL_RESOURCES_ID + pageName, exportAllTypesRadio.getSelection());
		}

		internalSaveWidgetValues();

	}

	protected void selectAppropriateFolderContents(IContainer resource) {
		try {
			IResource[] members = resource.members();

			for (IResource member : members) {
				if (member.getType() == IResource.FILE) {
					IFile currentFile = (IFile) member;
					if (hasExportableExtension(currentFile.getFullPath().toString())) {
