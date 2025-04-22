		return true;
	}

	public boolean isSaveNeeded() {
		return isDirty();
	}

	private boolean saveFile(File file) {

		File tempFile = new File(file.getAbsolutePath() + ".tmp"); //$NON-NLS-1$
		file.renameTo(tempFile);
		boolean saved = false;
		if (OLE.isOleFile(file) || usesStorageFiles(clientSite.getProgramID())) {
			saved = clientSite.save(file, true);
		} else {
			saved = clientSite.save(file, false);
		}

		if (saved) {
			tempFile.delete();
			return true;
		}
		tempFile.renameTo(file);
		return false;
	}

	private WorkspaceModifyOperation saveNewFileOperation() {

		return new WorkspaceModifyOperation() {
			@Override
