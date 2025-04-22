		Group g = new Group(main, SWT.NONE);
		g.setText(UIText.MergeTargetSelectionDialog_MergeTypeGroup);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(g);
		g.setLayout(new GridLayout(1, false));

		Button commit = new Button(g, SWT.RADIO);
