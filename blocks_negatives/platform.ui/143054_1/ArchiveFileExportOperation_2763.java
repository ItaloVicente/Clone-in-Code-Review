        while (resources.hasNext()) {
            IResource currentResource = resources.next();
            exportResource(currentResource);
        }
    }

    /**
     * Returns the status of the operation.
     * If there were any errors, the result is a status object containing
     * individual status objects for each error.
     * If there were no errors, the result is a status object with error code <code>OK</code>.
     *
     * @return the status
     */
    public IStatus getStatus() {
        IStatus[] errors = new IStatus[errorTable.size()];
        errorTable.toArray(errors);
        return new MultiStatus(
                IDEWorkbenchPlugin.IDE_WORKBENCH,
                IStatus.OK,
                errors,
                DataTransferMessages.FileSystemExportOperation_problemsExporting,
                null);
    }

    /**
     *	Initialize this operation
     *
     *	@exception java.io.IOException
     */
    protected void initialize() throws IOException {
    	if(useTarFormat) {
    		exporter = new TarFileExporter(destinationFilename, useCompression, resolveLinks);
    	} else {
        	exporter = new ZipFileExporter(destinationFilename, useCompression, resolveLinks);
    	}
    }

    /**
     *  Answer a boolean indicating whether the passed child is a descendent
     *  of one or more members of the passed resources collection
     *
     *  @return boolean
     *  @param resources java.util.Vector
     *  @param child org.eclipse.core.resources.IResource
     */
