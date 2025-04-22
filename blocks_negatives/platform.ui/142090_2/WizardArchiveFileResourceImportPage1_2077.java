    	if (ArchiveFileManipulations.isTarFile(sourceNameField.getText())) {
    		return ensureTarSourceIsValid();
    	}
    	return ensureZipSourceIsValid();
    }

    /**
     * The Finish button was pressed.  Try to do the required work now and answer
     * a boolean indicating success.  If <code>false</code> is returned then the
     * wizard will not close.
     *
     * @return boolean
     */
    @Override
