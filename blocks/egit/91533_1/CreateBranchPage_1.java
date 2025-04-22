		Composite nameComposite = new Composite(main, SWT.NONE);
		GridLayout gridLayout = new GridLayout(3, false);
		gridLayout.marginWidth = 0;
		gridLayout.marginHeight = 0;
		gridLayout.verticalSpacing = 0;
		gridLayout.horizontalSpacing = 4;
		nameComposite.setLayout(gridLayout);
		GridDataFactory.fillDefaults().span(4, 1).applyTo(nameComposite);

		Label nameLabel = new Label(nameComposite, SWT.NONE);
