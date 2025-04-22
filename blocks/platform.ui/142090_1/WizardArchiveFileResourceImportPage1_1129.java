			return false;
		}
		return ArchiveFileManipulations.closeZipFile(specifiedFile, getShell());
	}

	private boolean ensureTarSourceIsValid() {
		TarFile specifiedFile = getSpecifiedTarSourceFile();
		if( specifiedFile == null ) {
