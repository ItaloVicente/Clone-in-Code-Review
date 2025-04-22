		container = toolkit.createComposite(container);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		toolkit.paintBordersFor(container);
		GridLayoutFactory.swtDefaults().applyTo(container);

		Section messageSection = toolkit.createSection(container,
				ExpandableComposite.TITLE_BAR
						| ExpandableComposite.CLIENT_INDENT);
		messageSection.setText(UIText.CommitDialog_CommitMessage);
		Composite messageArea = toolkit.createComposite(messageSection);
		GridLayoutFactory.fillDefaults().spacing(0, 0)
				.extendedMargins(2, 2, 2, 2).applyTo(messageArea);
		toolkit.paintBordersFor(messageArea);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(messageSection);

		Composite headerArea = new Composite(messageSection, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(0, 0).numColumns(2)
				.applyTo(headerArea);

		ToolBar messageToolbar = new ToolBar(headerArea, SWT.FLAT
				| SWT.HORIZONTAL);
		GridDataFactory.fillDefaults().align(SWT.END, SWT.FILL)
				.grab(true, false).applyTo(messageToolbar);

		addMessageDropDown(headerArea);

		messageSection.setTextClient(headerArea);

		commitText = new SpellcheckableMessageArea(messageArea, commitMessage,
				SWT.NONE);
		commitText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		messageSection.setClient(messageArea);
