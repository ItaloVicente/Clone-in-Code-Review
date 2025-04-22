	}

	public TableElement(Table table, CSSEngine engine) {
		super(table, engine);
		fControlSelectedColorCustomization = new ControlSelectedColorCustomization(table,
				new TableControlSelectionEraseListener());
