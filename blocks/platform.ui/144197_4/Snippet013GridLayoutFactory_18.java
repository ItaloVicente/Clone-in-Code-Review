
		Label text = new Label(shell, SWT.NONE);
		text.setText("Name:");
		new Text(shell, SWT.BORDER);

		Label quest = new Label(shell, SWT.NONE);
		quest.setText("Quest:");
		CCombo combo = new CCombo(shell, SWT.BORDER);
		combo.add("I seek the holy grail");
		combo.add("What? I don't know that");
		combo.add("All your base are belong to us");

		Label colour = new Label(shell, SWT.NONE);
		colour.setText("Color:");
		new Text(shell, SWT.BORDER);

		Composite buttonBar = new Composite(shell, SWT.NONE);
		Button add = new Button(buttonBar, SWT.PUSH);
		add.setText("Okay");
		Button remove = new Button(buttonBar, SWT.PUSH);
		remove.setText("Cancel");

		GridLayoutFactory.fillDefaults().numColumns(2).generateLayout(
				buttonBar);
		GridDataFactory.fillDefaults().span(2, 1).align(SWT.RIGHT,
				SWT.BOTTOM).applyTo(buttonBar);

		GridLayoutFactory.fillDefaults().numColumns(2).margins(
				LayoutConstants.getMargins()).generateLayout(shell);
