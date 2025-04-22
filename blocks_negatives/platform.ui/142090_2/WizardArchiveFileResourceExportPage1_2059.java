    protected Button compressContentsCheckbox;

    private Button zipFormatButton;
    private Button targzFormatButton;




    /**
     *	Create an instance of this class.
     *
     *	@param name java.lang.String
     */
    protected WizardArchiveFileResourceExportPage1(String name,
            IStructuredSelection selection) {
        super(name, selection);
    }

    /**
     * Create an instance of this class
     * @param selection the selection
     */
    public WizardArchiveFileResourceExportPage1(IStructuredSelection selection) {
        this("zipFileExportPage1", selection); //$NON-NLS-1$
        setTitle(DataTransferMessages.ArchiveExport_exportTitle);
        setDescription(DataTransferMessages.ArchiveExport_description);
    }

    @Override
