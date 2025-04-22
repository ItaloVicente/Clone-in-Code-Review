		SashForm verticalSashForm = new SashForm(horizontalSashForm,
				SWT.VERTICAL);
		toolkit.adapt(verticalSashForm, true, true);
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(verticalSashForm);

		unstagedSection = toolkit.createSection(verticalSashForm,
				ExpandableComposite.TITLE_BAR);

		Composite unstagedTableComposite = toolkit
				.createComposite(unstagedSection);
		toolkit.paintBordersFor(unstagedTableComposite);
		unstagedSection.setClient(unstagedTableComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2)
				.applyTo(unstagedTableComposite);

		unstagedTableViewer = new TableViewer(toolkit.createTable(
				unstagedTableComposite, SWT.FULL_SELECTION | SWT.MULTI));
		GridDataFactory.fillDefaults().grab(true, true)
				.applyTo(unstagedTableViewer.getControl());
		unstagedTableViewer.getTable().setData(FormToolkit.KEY_DRAW_BORDER,
				FormToolkit.TREE_BORDER);
