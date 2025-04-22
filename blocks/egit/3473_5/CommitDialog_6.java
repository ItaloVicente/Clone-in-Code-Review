		Composite personArea = toolkit.createComposite(container);
		toolkit.paintBordersFor(personArea);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(personArea);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(personArea);

		toolkit.createLabel(personArea, UIText.CommitDialog_Author)
				.setForeground(
						toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
		authorText = toolkit.createText(personArea, null);
		authorText
				.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		authorText.setLayoutData(GridDataFactory.fillDefaults()
				.grab(true, false).create());
