    /**
     * Create an instance of this class
     * @param selection the selection
     */
    public WizardArchiveFileResourceExportPage1(IStructuredSelection selection) {
        this("zipFileExportPage1", selection); //$NON-NLS-1$
        setTitle(DataTransferMessages.ArchiveExport_exportTitle);
        setDescription(DataTransferMessages.ArchiveExport_description);
    }
