				headComposite, SWT.FLAT | SWT.WRAP, this);
		ToolBar bar = actionToolBarProvider.getTheToolbar();
		GridDataFactory.fillDefaults().grab(true, false)
				.align(SWT.END, SWT.CENTER).applyTo(bar);
		toolkit.adapt(bar);
		toolkit.paintBordersFor(bar);
		bar.setBackground(null);
		bar.pack();
