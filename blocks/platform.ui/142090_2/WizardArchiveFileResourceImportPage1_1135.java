		return DataTransferMessages.ArchiveImport_fromFile;
	}

	protected ZipFile getSpecifiedZipSourceFile() {
		return getSpecifiedZipSourceFile(sourceNameField.getText());
	}

	private ZipFile getSpecifiedZipSourceFile(String fileName) {
		if (fileName.length() == 0) {
