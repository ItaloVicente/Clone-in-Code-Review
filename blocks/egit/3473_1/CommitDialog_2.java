		Composite headerArea = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(false)
				.applyTo(headerArea);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(headerArea);

		Label label = new Label(headerArea, SWT.LEFT);
