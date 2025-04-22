		}
		ZipFile zipFile = getSpecifiedZipSourceFile(fileName);
		if(zipFile != null) {
			ArchiveFileManipulations.closeZipFile(zipFile, getShell());
			return true;
		}
		return false;
	}

	private boolean ensureZipSourceIsValid() {
		ZipFile specifiedFile = getSpecifiedZipSourceFile();
		if (specifiedFile == null) {
