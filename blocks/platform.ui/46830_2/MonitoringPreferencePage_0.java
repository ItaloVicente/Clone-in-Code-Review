
		createTopBlock(container);
		createBottomBlock(container, pixelConverter);

		GridLayoutFactory.fillDefaults()
				.numColumns(1)
				.spacing(LayoutConstants.getSpacing())
				.generateLayout(container);

		GridLayoutFactory.fillDefaults()
				.numColumns(1)
				.spacing(LayoutConstants.getSpacing())
				.generateLayout(parent);
	}

	private Composite createTopBlock(Composite container) {
		Composite block = new Composite(container, SWT.NONE);
