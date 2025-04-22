		this(createTable(parent, style));
	}

	public static CheckboxTableViewer newCheckList(Composite parent, int style) {
		Table table = new Table(parent, SWT.CHECK | style);
		return new CheckboxTableViewer(table);
	}

	public CheckboxTableViewer(Table table) {
		super(table);
	}

	@Override
