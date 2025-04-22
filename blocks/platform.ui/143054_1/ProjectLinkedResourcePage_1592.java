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


		TabFolder tabFolder = new TabFolder(pageComponent, SWT.TOP);
		tabFolder.addSelectionListener(new SelectionAdapter() {
