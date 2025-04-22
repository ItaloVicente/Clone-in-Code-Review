	protected Button compressContentsCheckbox;

	private Button zipFormatButton;
	private Button targzFormatButton;

	private static final String STORE_DESTINATION_NAMES_ID = "WizardZipFileResourceExportPage1.STORE_DESTINATION_NAMES_ID"; //$NON-NLS-1$

	private static final String STORE_CREATE_STRUCTURE_ID = "WizardZipFileResourceExportPage1.STORE_CREATE_STRUCTURE_ID"; //$NON-NLS-1$

	private static final String STORE_COMPRESS_CONTENTS_ID = "WizardZipFileResourceExportPage1.STORE_COMPRESS_CONTENTS_ID"; //$NON-NLS-1$

	protected WizardArchiveFileResourceExportPage1(String name,
			IStructuredSelection selection) {
		super(name, selection);
	}

	public WizardArchiveFileResourceExportPage1(IStructuredSelection selection) {
		this("zipFileExportPage1", selection); //$NON-NLS-1$
		setTitle(DataTransferMessages.ArchiveExport_exportTitle);
		setDescription(DataTransferMessages.ArchiveExport_description);
	}

	@Override
