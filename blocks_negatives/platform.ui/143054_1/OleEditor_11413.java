    	if (pathEditorInput != null)
    		source = new File(pathEditorInput.getPath().toOSString());

        if (input instanceof IFileEditorInput) {
        	if (resource == null)
        		ResourcesPlugin.getWorkspace().addResourceChangeListener(resourceListener);
        	resource = ((IFileEditorInput)input).getFile();
        } else if (resource != null) {
        	ResourcesPlugin.getWorkspace().removeResourceChangeListener(resourceListener);
        	resource = null;
        }

        super.setInputWithNotify(input);
    }

    /**
     * See if it is one of the known types that use OLE Storage.
     * @param progID the type to test
     * @return <code>true</code> if it is one of the known types
     */
    private static boolean usesStorageFiles(String progID) {
        return (progID != null && (progID.startsWith("Word.", 0) //$NON-NLS-1$
                || progID.startsWith("MSGraph", 0) //$NON-NLS-1$
                || progID.startsWith("PowerPoint", 0) //$NON-NLS-1$
        || progID.startsWith("Excel", 0))); //$NON-NLS-1$
    }

    /**
     * The source has changed to the newFile. Update
     * editors and set any required flags
     * @param newFile The file to get the new contents from.
     */
    private void sourceChanged(IFile newFile) {

        FileEditorInput newInput = new FileEditorInput(newFile);
        setInputWithNotify(newInput);
        sourceChanged = true;
        setPartName(newInput.getName());

    }

    @Override
