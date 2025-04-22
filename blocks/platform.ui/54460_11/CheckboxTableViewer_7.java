		this(createTable(parent, style));
	}

	public static <E, I> CheckboxTableViewer<E, I> newCheckList(
			Composite parent, int style) {
		Table table = new Table(parent, SWT.CHECK | style);
		return new CheckboxTableViewer<>(table);
	}

	public CheckboxTableViewer(Table table) {
		super(table);
	}

	@Override
