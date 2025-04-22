        Font font = parent.getFont();

        PlatformUI.getWorkbench().getHelpSystem().setHelp(parent,
                IIDEHelpContextIds.LINKED_RESOURCE_PREFERENCE_PAGE);
        Composite pageComponent = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        pageComponent.setLayout(layout);
        GridData data = new GridData();
        data.verticalAlignment = GridData.FILL;
        data.horizontalAlignment = GridData.FILL;
        pageComponent.setLayoutData(data);
        pageComponent.setFont(font);

        final Button enableLinkedResourcesButton = new Button(pageComponent,
                SWT.CHECK);
        enableLinkedResourcesButton.setText(IDEWorkbenchMessages.LinkedResourcesPreference_enableLinkedResources);
        enableLinkedResourcesButton.setFont(font);
        enableLinkedResourcesButton
                .addSelectionListener(new SelectionAdapter() {
                    @Override
