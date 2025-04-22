        super.createControl(parent);
        Composite composite = (Composite) getControl();

        PlatformUI.getWorkbench().getHelpSystem().setHelp(composite,
				IReadmeConstants.CREATION_WIZARD_PAGE_CONTEXT);


        Group group = new Group(composite, SWT.NONE);
        group.setLayout(new GridLayout());
        group.setText(MessageUtil
        group.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.HORIZONTAL_ALIGN_FILL));

        sectionCheckbox = new Button(group, SWT.CHECK);
        sectionCheckbox.setText(MessageUtil
        sectionCheckbox.setSelection(true);
        sectionCheckbox.addListener(SWT.Selection, this);

        subsectionCheckbox = new Button(group, SWT.CHECK);
        subsectionCheckbox.setText(MessageUtil
        subsectionCheckbox.setSelection(true);
        subsectionCheckbox.addListener(SWT.Selection, this);

        openFileCheckbox = new Button(composite, SWT.CHECK);
        openFileCheckbox.setText(MessageUtil
        openFileCheckbox.setSelection(true);

        setPageComplete(validatePage());

    }

    /**
     * Creates a new file resource as requested by the user. If everything
     * is OK then answer true. If not, false will cause the dialog
     * to stay open.
     *
     * @return whether creation was successful
     * @see ReadmeCreationWizard#performFinish()
     */
    public boolean finish() {
        IFile newFile = createNewFile();
        if (newFile == null)

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

    /**
     * The <code>ReadmeCreationPage</code> implementation of this
     * <code>WizardNewFileCreationPage</code> method
     * generates sample headings for sections and subsections in the
     * newly-created Readme file according to the selections of self's
     * checkbox widgets
     */
    @Override
