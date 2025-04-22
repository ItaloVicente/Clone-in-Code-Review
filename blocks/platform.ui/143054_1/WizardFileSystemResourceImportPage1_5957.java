	protected WizardFileSystemResourceImportPage1(String name,
			IWorkbench aWorkbench, IStructuredSelection selection) {
		super(name, selection);
	}

	public WizardFileSystemResourceImportPage1(IWorkbench aWorkbench,
			IStructuredSelection selection) {
		this("fileSystemImportPage1", aWorkbench, selection);//$NON-NLS-1$
		setTitle(DataTransferMessages.DataTransfer_fileSystemTitle);
		setDescription(DataTransferMessages.FileImport_importFileSystem);
	}

	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());

		GridData buttonData = new GridData(GridData.FILL_HORIZONTAL);
		button.setLayoutData(buttonData);

		button.setData(id);
		button.setText(label);

		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
			button.setFocus();
		}
		return button;
	}

	protected final void createButtonsGroup(Composite parent) {
		Composite buttonComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.makeColumnsEqualWidth = true;
		buttonComposite.setLayout(layout);
		buttonComposite.setFont(parent.getFont());
		GridData buttonData = new GridData(SWT.FILL, SWT.FILL, true, false);
		buttonComposite.setLayoutData(buttonData);

		selectTypesButton = createButton(buttonComposite,
				IDialogConstants.SELECT_TYPES_ID, SELECT_TYPES_TITLE, false);

		SelectionListener listener = new SelectionAdapter() {
			@Override
