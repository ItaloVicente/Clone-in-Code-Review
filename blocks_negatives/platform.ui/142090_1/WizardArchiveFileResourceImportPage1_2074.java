    	}
    	ZipFile zipFile = getSpecifiedZipSourceFile(fileName);
    	if(zipFile != null) {
    		ArchiveFileManipulations.closeZipFile(zipFile, getShell());
    		return true;
    	}
    	return false;
    }

    /**
     *	Answer a boolean indicating whether the specified source currently exists
     *	and is valid (ie.- proper format)
     */
    private boolean ensureZipSourceIsValid() {
        ZipFile specifiedFile = getSpecifiedZipSourceFile();
        if (specifiedFile == null) {
