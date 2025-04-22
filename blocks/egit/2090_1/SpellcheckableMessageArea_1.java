		Composite composite = new Composite(this, SWT.BORDER);

		GridLayout layout = new GridLayout(2, true);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		composite.setLayout(layout);

		ViewForm viewForm = new ViewForm(composite, SWT.FLAT);
        viewForm.marginHeight = 0;
        viewForm.marginWidth = 0;
        viewForm.verticalSpacing = 0;
        viewForm.setBorderVisible(false);
        viewForm.setLayoutData(GridDataFactory.fillDefaults().span(2,1).create());

        ToolBar toolBar = new ToolBar(viewForm, SWT.FLAT | SWT.WRAP);
        configureToolbar(toolBar);
	    viewForm.setTopRight(toolBar);

	    CLabel label = new CLabel(viewForm, SWT.NONE);
	    label.setText("Message"); //$NON-NLS-1$
	    viewForm.setTopLeft(label);

	    Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
	    separator.setLayoutData(GridDataFactory.fillDefaults().span(2,1).create());

