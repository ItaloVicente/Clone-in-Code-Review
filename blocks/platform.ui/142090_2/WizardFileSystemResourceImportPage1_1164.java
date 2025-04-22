		updateWidgetEnablements();
		fileSystemStructureProvider.clearVisitedDirs();
		selectionGroup.setFocus();
	}

	protected MinimizedFileSystemElement createRootElement(
			Object fileSystemObject, IImportStructureProvider provider) {
		boolean isContainer = provider.isFolder(fileSystemObject);
		String elementLabel = provider.getLabel(fileSystemObject);

		MinimizedFileSystemElement dummyParent = new MinimizedFileSystemElement(
				"", null, true);//$NON-NLS-1$
		dummyParent.setPopulated();
		MinimizedFileSystemElement result = new MinimizedFileSystemElement(
				elementLabel, dummyParent, isContainer);
		result.setFileSystemObject(fileSystemObject);

		result.getFiles(provider);

		return dummyParent;
	}

	@Override
