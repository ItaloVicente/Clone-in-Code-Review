		Composite locationNameComposite = new Composite(grpWindowTitle, SWT.NONE);
		GridDataFactory.defaultsFor(locationNameComposite).indent(0, 0).applyTo(locationNameComposite);
		GridLayout locationNameLayout = new GridLayout(2, false);
		locationNameComposite.setLayout(locationNameLayout);
		locationNameLayout.marginWidth = locationNameLayout.marginHeight = 0;
		showLocationNameInTitle = new Button(locationNameComposite, SWT.CHECK);
