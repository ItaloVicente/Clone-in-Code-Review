	private IWorkbench workbench;

	private Button sectionCheckbox;

	private Button subsectionCheckbox;

	private Button openFileCheckbox;

	private static int nameCounter = 1;

	public ReadmeCreationPage(IWorkbench workbench, IStructuredSelection selection) {
		super("sampleCreateReadmePage1", selection); //$NON-NLS-1$
		this.setTitle(MessageUtil.getString("Create_Readme_File")); //$NON-NLS-1$
		this.setDescription(MessageUtil.getString("Create_a_new_Readme_file_resource")); //$NON-NLS-1$
		this.workbench = workbench;
	}

	@Override
