		return ensureTargetIsValid(new File(fullPathname.substring(0,
				separatorIndex)));
	}

	protected boolean ensureTargetFileIsValid(File targetFile) {
		if (targetFile.exists() && targetFile.isDirectory()) {
			displayErrorDialog(DataTransferMessages.ZipExport_mustBeFile);
			giveFocusToDestination();
			return false;
		}

		if (targetFile.exists()) {
			if (targetFile.canWrite()) {
				if (!queryYesNoQuestion(DataTransferMessages.ZipExport_alreadyExists)) {
