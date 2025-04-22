		return true;
	}

	protected boolean executeExportOperation(ArchiveFileExportOperation op) {
		op.setCreateLeadupStructure(createDirectoryStructureButton
				.getSelection());
		op.setUseCompression(compressContentsCheckbox.getSelection());
		op.setIncludeLinkedResources(resolveLinkedResourcesCheckbox.getSelection());
		op.setUseTarFormat(targzFormatButton.getSelection());

		try {
			getContainer().run(true, true, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			displayErrorDialog(e.getTargetException());
