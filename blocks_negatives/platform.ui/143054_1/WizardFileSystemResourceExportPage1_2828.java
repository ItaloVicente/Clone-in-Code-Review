    /**
     * Create an instance of this class.
     *
     * @param selection the selection
     */
    public WizardFileSystemResourceExportPage1(IStructuredSelection selection) {
        this("fileSystemExportPage1", selection); //$NON-NLS-1$
        setTitle(DataTransferMessages.DataTransfer_fileSystemTitle);
        setDescription(DataTransferMessages.FileExport_exportLocalFileSystem);
    }
