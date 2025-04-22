    private static final String SELECT_DESTINATION_MESSAGE = DataTransferMessages.FileExport_selectDestinationMessage;

    private static final String SELECT_DESTINATION_TITLE = DataTransferMessages.FileExport_selectDestinationTitle;


    /**
     *	Create an instance of this class
     */
    protected WizardFileSystemResourceExportPage1(String name,
            IStructuredSelection selection) {
        super(name, selection);
    }

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

    /**
     *	Add the passed value to self's destination widget's history
     *
     *	@param value java.lang.String
     */
    protected void addDestinationItem(String value) {
        destinationNameField.add(value);
    }

    @Override
