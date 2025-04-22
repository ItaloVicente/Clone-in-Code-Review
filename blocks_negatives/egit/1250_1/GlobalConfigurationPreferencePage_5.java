		final Composite main = new Composite(parent, SWT.NONE);
		main.setLayout(new GridLayout(1, false));

		Composite locationPanel = new Composite(main, SWT.NONE);
		locationPanel.setLayout(new GridLayout(3, false));
		GridDataFactory.fillDefaults().grab(true, false).applyTo(locationPanel);
		Label locationLabel = new Label(locationPanel, SWT.NONE);
		locationLabel
				.setText(UIText.GlobalConfigurationPreferencePage_ConfigLocationLabel);
		location = new Text(locationPanel, SWT.BORDER);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true,
				false).applyTo(location);
		location.setEditable(false);
		Button openEditor = new Button(locationPanel, SWT.PUSH);
		openEditor
				.setText(UIText.GlobalConfigurationPreferencePage_OpenEditorButton);
		openEditor
				.setToolTipText(UIText.GlobalConfigurationPreferencePage_OpenEditorTooltip);
		openEditor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IFileStore store = EFS.getLocalFileSystem().getStore(
						new Path(userConfig.getFile().getAbsolutePath()));
				try {
					IDE.openEditor(PlatformUI.getWorkbench()
							.getActiveWorkbenchWindow().getActivePage(),
							new FileStoreEditorInput(store),
							EditorsUI.DEFAULT_TEXT_EDITOR_ID);
				} catch (PartInitException ex) {
					Activator.handleError(ex.getMessage(), ex, true);
				}
			}
		});

		tv = new TreeViewer(main, SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
		Tree tree = tv.getTree();
		GridDataFactory.fillDefaults().hint(100, 60).grab(true, true).applyTo(tree);
		TreeColumn key = new TreeColumn(tree, SWT.NONE);
		key.setText(UIText.GlobalConfigurationPreferencePage_KeyColumnHeader);
		key.setWidth(150);

		TreeColumn value = new TreeColumn(tree, SWT.NONE);
		value.setText(UIText.GlobalConfigurationPreferencePage_ValueColumnHeader);
		value.setWidth(250);

		tv.setContentProvider(new ContentProvider());
		tv.setLabelProvider(new LabelProvider());

		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		Composite valuePanel = new Composite(main, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(valuePanel);
		valuePanel.setLayout(new GridLayout(5, false));
		new Label(valuePanel, SWT.NONE)
				.setText(UIText.GlobalConfigurationPreferencePage_ValueLabel);
		valueText = new Text(valuePanel, SWT.BORDER);
		valueText
				.setText(UIText.GlobalConfigurationPreferencePage_NoEntrySelectedMessage);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(valueText);
		applyValue = new Button(valuePanel, SWT.PUSH);
		applyValue
				.setText(UIText.GlobalConfigurationPreferencePage_ChangeButton);
		applyValue.addSelectionListener(new SelectionAdapter() {
