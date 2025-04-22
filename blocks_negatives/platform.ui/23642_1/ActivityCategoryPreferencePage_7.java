    	initializeDialogUnits(parent);
    	
        Composite composite = new Composite(parent, SWT.NONE);  
        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = layout.marginWidth = 0;
        layout.horizontalSpacing = convertHorizontalDLUsToPixels(IDialogConstants.HORIZONTAL_SPACING);
        layout.verticalSpacing = convertVerticalDLUsToPixels(IDialogConstants.VERTICAL_SPACING);
        composite.setLayout(layout);
        Label label = new Label(composite, SWT.WRAP);
        label
                .setText(strings.getProperty(CAPTION_MESSAGE, ActivityMessages.ActivitiesPreferencePage_captionMessage));
        GridData data = new GridData(GridData.FILL_HORIZONTAL);
        data.widthHint = 400;
        data.horizontalSpan = 2;        
        label.setLayoutData(data);
        label = new Label(composite, SWT.NONE); //spacer
        data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
        data.horizontalSpan = 2;
        label.setLayoutData(data);
        createPromptButton(composite);
        createCategoryArea(composite);
        createDetailsArea(composite);
        createButtons(composite);
        
        workbench.getHelpSystem().setHelp(parent,
