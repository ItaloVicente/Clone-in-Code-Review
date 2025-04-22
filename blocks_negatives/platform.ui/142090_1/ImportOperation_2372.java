           if(!status.isOK()){
           		errorTable.add(status);
           		ArrayList filteredFiles = new ArrayList();

           		for (IFile file : files) {
           			filteredFiles.add(file.getFullPath());
           		}
           		return filteredFiles;
           }

        }
        return new ArrayList();
    }

    /**
     * Validates the given file system objects.
     * The user is prompted to overwrite existing files.
     * Existing read-only files are validated with the VCM provider.
     *
     * @param sourceFiles files to validate
     */
