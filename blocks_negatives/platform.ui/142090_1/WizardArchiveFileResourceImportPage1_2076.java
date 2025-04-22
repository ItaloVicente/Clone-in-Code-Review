    		return false;
    	}
    	return ArchiveFileManipulations.closeTarFile(specifiedFile, getShell());
    }

    /**
     *	Answer a boolean indicating whether the specified source currently exists
     *	and is valid (ie.- proper format)
     */
    @Override
