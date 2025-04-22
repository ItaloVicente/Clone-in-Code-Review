        Composite composite = (Composite) super.createDialogArea(parent);

        ArrayList input = new ArrayList();
        input.add(root);

        createMessageArea(composite);
        selectionGroup = new CheckboxTreeAndListGroup(composite, input,
                getResourceProvider(IResource.FOLDER | IResource.PROJECT
                        | IResource.ROOT), WorkbenchLabelProvider
                        .getDecoratingWorkbenchLabelProvider(),
                        new ResourceComparator(ResourceComparator.NAME),
                getResourceProvider(IResource.FILE), WorkbenchLabelProvider
                        .getDecoratingWorkbenchLabelProvider(),
                        new ResourceComparator(ResourceComparator.NAME),SWT.NONE,
                SIZING_SELECTION_WIDGET_WIDTH, SIZING_SELECTION_WIDGET_HEIGHT);

        composite.addControlListener(new ControlListener() {
            @Override
