		Label text = new Label(shell, SWT.WRAP);
		text
				.setText("This is a layout test. This text should wrap in the test. You could call it a text test.");
		GridDataFactory.generate(text, 2, 1);

		List theList = new List(shell, SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.BORDER);

		theList.add("Hello");
		theList.add("World");
		GridDataFactory.defaultsFor(theList).hint(300, 300)
				.applyTo(theList);

		Composite buttonBar = new Composite(shell, SWT.NONE);
		Button add = new Button(buttonBar, SWT.PUSH);
		add.setText("Add");
		Button remove = new Button(buttonBar, SWT.PUSH);
		remove.setText("Remove");
		GridLayoutFactory.fillDefaults().generateLayout(buttonBar);
