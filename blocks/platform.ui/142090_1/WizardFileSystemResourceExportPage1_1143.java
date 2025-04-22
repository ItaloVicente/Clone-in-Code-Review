	private static final String SELECT_DESTINATION_MESSAGE = DataTransferMessages.FileExport_selectDestinationMessage;

	private static final String SELECT_DESTINATION_TITLE = DataTransferMessages.FileExport_selectDestinationTitle;


	protected WizardFileSystemResourceExportPage1(String name,
			IStructuredSelection selection) {
		super(name, selection);
	}

	public WizardFileSystemResourceExportPage1(IStructuredSelection selection) {
		this("fileSystemExportPage1", selection); //$NON-NLS-1$
		setTitle(DataTransferMessages.DataTransfer_fileSystemTitle);
		setDescription(DataTransferMessages.FileExport_exportLocalFileSystem);
	}

	protected void addDestinationItem(String value) {
		destinationNameField.add(value);
	}

	@Override
