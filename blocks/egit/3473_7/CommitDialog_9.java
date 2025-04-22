		filesSection = toolkit.createSection(container,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(filesSection);
		Composite filesArea = toolkit.createComposite(filesSection);
		filesSection.setClient(filesArea);
		toolkit.paintBordersFor(filesArea);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(filesArea);

		ToolBar filesToolbar = new ToolBar(filesSection, SWT.FLAT);

		filesSection.setTextClient(filesToolbar);

		Table resourcesTable = toolkit.createTable(filesArea, SWT.H_SCROLL
				| SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.MULTI | SWT.CHECK);
		resourcesTable.setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
		resourcesTable.setLayoutData(GridDataFactory.fillDefaults()
				.hint(600, 200).grab(true, true).create());
