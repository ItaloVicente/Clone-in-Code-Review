			} else {
				displayErrorDialog(DataTransferMessages.ZipExport_alreadyExistsError);
				giveFocusToDestination();
				return false;
			}
		}

		return true;
	}

	protected boolean ensureTargetIsValid() {
		String targetPath = getDestinationValue();

		if (!ensureTargetDirectoryIsValid(targetPath)) {
			return false;
		}

		if (!ensureTargetFileIsValid(new File(targetPath))) {
			return false;
		}

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
