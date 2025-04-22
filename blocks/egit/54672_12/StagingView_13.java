		.setForeground(
				toolkit.getColors().getColor(IFormColors.TB_TOGGLE));
authorText = toolkit.createText(composite, null);
authorText
		.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
authorText.setLayoutData(GridDataFactory.fillDefaults()
		.grab(true, false).create());
