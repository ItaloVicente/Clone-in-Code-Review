		super.createControl(parent);
		Composite composite = (Composite) getControl();

		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IReadmeConstants.CREATION_WIZARD_PAGE_CONTEXT);

		this.setFileName("sample" + nameCounter + ".readme"); //$NON-NLS-1$ //$NON-NLS-2$

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout());
		group.setText(MessageUtil.getString("Automatic_sample_section_generation")); //$NON-NLS-1$
		group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));

		sectionCheckbox = new Button(group, SWT.CHECK);
		sectionCheckbox.setText(MessageUtil.getString("Generate_sample_section_titles")); //$NON-NLS-1$
		sectionCheckbox.setSelection(true);
		sectionCheckbox.addListener(SWT.Selection, this);

		subsectionCheckbox = new Button(group, SWT.CHECK);
		subsectionCheckbox.setText(MessageUtil.getString("Generate_sample_subsection_titles")); //$NON-NLS-1$
		subsectionCheckbox.setSelection(true);
		subsectionCheckbox.addListener(SWT.Selection, this);

		openFileCheckbox = new Button(composite, SWT.CHECK);
		openFileCheckbox.setText(MessageUtil.getString("Open_file_for_editing_when_done")); //$NON-NLS-1$
		openFileCheckbox.setSelection(true);

		setPageComplete(validatePage());

	}

	public boolean finish() {
		IFile newFile = createNewFile();
		if (newFile == null)
			return false; // ie.- creation was unsuccessful

		try {
			if (openFileCheckbox.getSelection()) {
				IWorkbenchWindow dwindow = workbench.getActiveWorkbenchWindow();
				IWorkbenchPage page = dwindow.getActivePage();
				if (page != null) {
					IDE.openEditor(page, newFile, true);
				}
			}
		} catch (PartInitException e) {
			e.printStackTrace();
			return false;
		}
		nameCounter++;
		return true;
	}

	@Override
