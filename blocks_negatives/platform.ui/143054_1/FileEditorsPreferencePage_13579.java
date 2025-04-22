        imagesToDispose = new ArrayList();
        editorsToImages = new HashMap(50);

        Composite pageComponent = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        pageComponent.setLayout(layout);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        pageComponent.setLayoutData(data);


        PreferenceLinkArea contentTypeArea = new PreferenceLinkArea(pageComponent, SWT.NONE,
                "org.eclipse.ui.preferencePages.ContentTypes", WorkbenchMessages.FileEditorPreference_contentTypesRelatedLink,//$NON-NLS-1$
                (IWorkbenchPreferenceContainer) getContainer(),null);

        data = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
        contentTypeArea.getControl().setLayoutData(data);

        Label label = new Label(pageComponent, SWT.LEFT);
        label.setText(WorkbenchMessages.FileEditorPreference_fileTypes);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        resourceTypeTable = new Table(pageComponent, SWT.MULTI | SWT.BORDER
                | SWT.FULL_SELECTION);
        resourceTypeTable.addListener(SWT.Selection, this);
        resourceTypeTable.addListener(SWT.DefaultSelection, this);
        data = new GridData(GridData.FILL_HORIZONTAL);

        int availableRows = DialogUtil.availableRows(pageComponent);

        data.heightHint = resourceTypeTable.getItemHeight()
                * (availableRows / 8);
        resourceTypeTable.setLayoutData(data);

        Composite groupComponent = new Composite(pageComponent, SWT.NULL);
        GridLayout groupLayout = new GridLayout();
        groupLayout.marginWidth = 0;
        groupLayout.marginHeight = 0;
        groupComponent.setLayout(groupLayout);
        data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        groupComponent.setLayoutData(data);

        addResourceTypeButton = new Button(groupComponent, SWT.PUSH);
        addResourceTypeButton.setText(WorkbenchMessages.FileEditorPreference_add);
        addResourceTypeButton.addListener(SWT.Selection, this);
        setButtonLayoutData(addResourceTypeButton);

        removeResourceTypeButton = new Button(groupComponent, SWT.PUSH);
        removeResourceTypeButton.setText(WorkbenchMessages.FileEditorPreference_remove);
        removeResourceTypeButton.addListener(SWT.Selection, this);
        setButtonLayoutData(removeResourceTypeButton);

        label = new Label(pageComponent, SWT.LEFT);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        label.setLayoutData(data);

        editorLabel = new Label(pageComponent, SWT.LEFT);
        editorLabel.setText(WorkbenchMessages.FileEditorPreference_associatedEditors);
        data = new GridData();
        data.horizontalAlignment = GridData.FILL;
        data.horizontalSpan = 2;
        editorLabel.setLayoutData(data);

        editorTable = new Table(pageComponent, SWT.MULTI | SWT.BORDER);
        editorTable.addListener(SWT.Selection, this);
        editorTable.addListener(SWT.DefaultSelection, this);
        data = new GridData(GridData.FILL_BOTH);
        data.heightHint = editorTable.getItemHeight() * 7;
        editorTable.setLayoutData(data);

        groupComponent = new Composite(pageComponent, SWT.NULL);
        groupLayout = new GridLayout();
        groupLayout.marginWidth = 0;
        groupLayout.marginHeight = 0;
        groupComponent.setLayout(groupLayout);
        data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        groupComponent.setLayoutData(data);

        addEditorButton = new Button(groupComponent, SWT.PUSH);
        addEditorButton.setText(WorkbenchMessages.FileEditorPreference_addEditor);
        addEditorButton.addListener(SWT.Selection, this);
        addEditorButton.setLayoutData(data);
        setButtonLayoutData(addEditorButton);

        removeEditorButton = new Button(groupComponent, SWT.PUSH);
        removeEditorButton.setText(WorkbenchMessages.FileEditorPreference_removeEditor);
        removeEditorButton.addListener(SWT.Selection, this);
        setButtonLayoutData(removeEditorButton);

        defaultEditorButton = new Button(groupComponent, SWT.PUSH);
        defaultEditorButton.setText(WorkbenchMessages.FileEditorPreference_default);
        defaultEditorButton.addListener(SWT.Selection, this);
        setButtonLayoutData(defaultEditorButton);

        fillResourceTypeTable();
        if (resourceTypeTable.getItemCount() > 0) {
            resourceTypeTable.setSelection(0);
        }
        fillEditorTable();
        updateEnabledState();

        workbench.getHelpSystem().setHelp(parent,
				IWorkbenchHelpContextIds.FILE_EDITORS_PREFERENCE_PAGE);
        applyDialogFont(pageComponent);

        return pageComponent;
    }

    /**
     * The preference page is going to be disposed. So deallocate all allocated
     * SWT resources that aren't disposed automatically by disposing the page
     * (i.e fonts, cursors, etc). Subclasses should reimplement this method to
     * release their own allocated SWT resources.
     */
    @Override
