		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.END)
				.grab(true, false).applyTo(label);

		ToolBar messageToolbar = new ToolBar(headerArea, SWT.FLAT | SWT.RIGHT);
		GridDataFactory.fillDefaults().applyTo(messageToolbar);

		addMessageDropDown(headerArea);
