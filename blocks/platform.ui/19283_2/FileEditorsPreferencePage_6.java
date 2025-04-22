	public void addResourceType(String pattern) {
		IFileTypeProcessor fileTypeProcessor = new FileTypeProcessor();
		if (!fileTypeProcessor.isValidSuffixPattern(pattern)
				&& !fileTypeProcessor.isValidFileType(pattern)) {
			throw new IllegalArgumentException("Pattern does not seem to be valid for mapping."); //$NON-NLS-1$
		}

		IFileEditorMapping resourceType;
		TableItem[] items = resourceTypeTable.getItems();
		boolean found = false;
		int i = 0;

		while (i < items.length && !found) {
			resourceType = (IFileEditorMapping) items[i].getData();
			int result = pattern.compareToIgnoreCase(resourceType.getLabel());
			if (result == 0) {
				MessageDialog.openInformation(getControl().getShell(),
						WorkbenchMessages.FileEditorPreference_existsTitle,
						WorkbenchMessages.FileEditorPreference_existsMessage);
				return;
			}

			if (result < 0) {
				found = true;
			} else {
				i++;
			}
		}

		if (fileTypeProcessor.isValidSuffixPattern(pattern)) {
			String prefix = fileTypeProcessor.getPrefix(pattern);
			String suffix = fileTypeProcessor.getSuffix(pattern);
			resourceType = new FileEditorMapping(prefix, suffix);
		} else {
			String newName = fileTypeProcessor.getName(pattern);
			String newExtension = fileTypeProcessor.getExtension(pattern);
			resourceType = new FileEditorMapping(newName, newExtension);
		}
		TableItem item = newResourceTableItem(resourceType, i, true);
		resourceTypeTable.setFocus();
		resourceTypeTable.showItem(item);
		fillEditorTable();
	}

