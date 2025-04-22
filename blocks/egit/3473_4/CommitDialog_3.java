		Composite headerArea = new Composite(container, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(3)
				.equalWidth(false).applyTo(headerArea);
		GridDataFactory.fillDefaults().span(2, 1).applyTo(headerArea);

		Label label = new Label(headerArea, SWT.LEFT);
