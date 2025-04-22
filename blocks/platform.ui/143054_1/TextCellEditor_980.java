	protected Text text;

	private ModifyListener modifyListener;

	private boolean isSelection = false;

	private boolean isDeleteable = false;

	private boolean isSelectable = false;

	private static final int defaultStyle = SWT.SINGLE;

	public TextCellEditor() {
		setStyle(defaultStyle);
	}

	public TextCellEditor(Composite parent) {
		this(parent, defaultStyle);
	}

	public TextCellEditor(Composite parent, int style) {
		super(parent, style);
	}

	private void checkDeleteable() {
		boolean oldIsDeleteable = isDeleteable;
		isDeleteable = isDeleteEnabled();
		if (oldIsDeleteable != isDeleteable) {
			fireEnablementChanged(DELETE);
		}
	}

	private void checkSelectable() {
		boolean oldIsSelectable = isSelectable;
		isSelectable = isSelectAllEnabled();
		if (oldIsSelectable != isSelectable) {
			fireEnablementChanged(SELECT_ALL);
		}
	}

	private void checkSelection() {
		boolean oldIsSelection = isSelection;
		isSelection = text.getSelectionCount() > 0;
		if (oldIsSelection != isSelection) {
			fireEnablementChanged(COPY);
			fireEnablementChanged(CUT);
		}
	}

	@Override
