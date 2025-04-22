		toolkit.paintBordersFor(bodyComposite);
		GridLayoutFactory.fillDefaults().extendedMargins(2, 2, 2, 2).applyTo(bodyComposite);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(bodyComposite);

		Composite tableComposite = toolkit.createComposite(bodyComposite);
		final TableColumnLayout layout = new TableColumnLayout();
		tableComposite.setLayout(layout);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(tableComposite);
