        this.newProjectRadio = new Button(containerGroup, SWT.RADIO);
        this.newProjectRadio.setText(IDEWorkbenchMessages.WizardImportPage_ImportAsNewProject);
        this.newProjectRadio.setFont(parent.getFont());
        this.newProjectRadio.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
        
        this.existingProjectRadio = new Button(containerGroup, SWT.RADIO);
        this.existingProjectRadio.setText(IDEWorkbenchMessages.WizardImportPage_ImportExistingProject);
        this.existingProjectRadio.setFont(parent.getFont());
        this.existingProjectRadio.setLayoutData(new GridData(SWT.NONE, SWT.NONE, false, false, 3, 1));
        
        resourcesRadio = new Button(containerGroup, SWT.RADIO);
        resourcesRadio.setText(IDEWorkbenchMessages.WizardImportPage_folder);
        resourcesRadio.setFont(parent.getFont());
