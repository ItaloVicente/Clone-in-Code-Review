	                .getPropertyValue(ButtonElementProperties.PROPERTY_SIZE);
            sizePropertySource.setPropertyValue(SizePropertySource.ID_HEIGHT,
                heightText.getText());
            sizePropertySource.setPropertyValue(SizePropertySource.ID_WIDTH,
                widthText.getText());
        }
    };

    public void setInput(IWorkbenchPart part, ISelection selection) {
        super.setInput(part, selection);
        Assert.isTrue(selection instanceof IStructuredSelection);
        Object input = ((IStructuredSelection) selection).getFirstElement();
        Assert.isTrue(input instanceof ButtonElement);
        this.buttonElement = (ButtonElement) input;
    }

    /**
     * @see org.eclipse.ui.views.properties.tabbed.ITabbedPropertySection#createControls(org.eclipse.swt.widgets.Composite,
     *      org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage)
     */
    public void createControls(Composite parent,
            TabbedPropertySheetPage tabbedPropertySheetPage) {
        super.createControls(parent, tabbedPropertySheetPage);
        Composite composite = getWidgetFactory()
            .createFlatFormComposite(parent);
        FormData data;

        CLabel widthLabel = getWidgetFactory()
            .createCLabel(composite, "Width:"); //$NON-NLS-1$
        widthText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$
        CLabel heightLabel = getWidgetFactory().createCLabel(composite,
        heightText = getWidgetFactory().createText(composite, ""); //$NON-NLS-1$

        data = new FormData();
        data.left = new FormAttachment(0, 0);
        data.right = new FormAttachment(widthText,
            -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(0, 0);
        widthLabel.setLayoutData(data);

        data = new FormData();
        data.left = new FormAttachment(0, STANDARD_LABEL_WIDTH);
        data.right = new FormAttachment(heightLabel,
            -ITabbedPropertyConstants.HSPACE);
        data.top = new FormAttachment(0, 0);
        widthText.setLayoutData(data);

        data = new FormData();
        data.left = new FormAttachment(50, 0);
        data.top = new FormAttachment(0, 0);
        heightLabel.setLayoutData(data);

        data = new FormData();
        data.left = new FormAttachment(heightLabel,
            ITabbedPropertyConstants.HSPACE);
        data.right = new FormAttachment(100, 0);
        data.top = new FormAttachment(0, 0);
        heightText.setLayoutData(data);

        heightText.addModifyListener(listener);
        widthText.addModifyListener(listener);
    }

    /*
     * @see org.eclipse.ui.views.properties.tabbed.view.ITabbedPropertySection#refresh()
     */
    public void refresh() {
        heightText.removeModifyListener(listener);
        widthText.removeModifyListener(listener);
