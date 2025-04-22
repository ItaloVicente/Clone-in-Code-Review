        this.referenceAsNewProjectRadio = new Button(containerGroup, SWT.RADIO);
        this.referenceAsNewProjectRadio.setText(IDEWorkbenchMessages.WizardImportPage_ImportAsReferenceNewProject);
        this.referenceAsNewProjectRadio.setFont(parent.getFont());
        this.referenceAsNewProjectRadio.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
        
        this.copyAsNewProjectRadio = new Button(containerGroup, SWT.RADIO);
        this.copyAsNewProjectRadio.setText(IDEWorkbenchMessages.WizardImportPage_ImportAsCopiedNewProject);
        this.copyAsNewProjectRadio.setFont(parent.getFont());
        this.copyAsNewProjectRadio.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
        
        this.referenceExistingProjectRadio = new Button(containerGroup, SWT.RADIO);
        this.referenceExistingProjectRadio.setText(IDEWorkbenchMessages.WizardImportPage_ImportExistingProject);
        this.referenceExistingProjectRadio.setFont(parent.getFont());
        this.referenceExistingProjectRadio.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
        
        resourcesRadio = new Button(containerGroup, SWT.RADIO);
        resourcesRadio.setText(IDEWorkbenchMessages.WizardImportPage_folder);
        resourcesRadio.setFont(parent.getFont());
