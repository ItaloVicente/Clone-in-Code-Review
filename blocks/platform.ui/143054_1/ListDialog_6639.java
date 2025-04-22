	private IStructuredContentProvider fContentProvider;

	private ILabelProvider fLabelProvider;

	private Object fInput;

	private TableViewer fTableViewer;

	private boolean fAddCancelButton = true;

	private int widthInChars = 55;

	private int heightInChars = 15;

	public ListDialog(Shell parent) {
		super(parent);
	}

	public void setInput(Object input) {
		fInput = input;
	}

	public void setContentProvider(IStructuredContentProvider sp) {
		fContentProvider = sp;
	}

	public void setLabelProvider(ILabelProvider lp) {
		fLabelProvider = lp;
	}

	public void setAddCancelButton(boolean addCancelButton) {
		fAddCancelButton = addCancelButton;
	}

	public TableViewer getTableViewer() {
		return fTableViewer;
	}

	@Override
