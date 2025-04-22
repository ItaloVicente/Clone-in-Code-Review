	private int minValidValue = 0;

	private int maxValidValue = Integer.MAX_VALUE;

	private static final int DEFAULT_TEXT_LIMIT = 10;

	protected IntegerFieldEditor() {
	}

	public IntegerFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
	}

	public IntegerFieldEditor(String name, String labelText, Composite parent,
			int textLimit) {
		init(name, labelText);
		setTextLimit(textLimit);
		setEmptyStringAllowed(false);
		setErrorMessage(JFaceResources
				.getString("IntegerFieldEditor.errorMessage"));//$NON-NLS-1$
		createControl(parent);
	}

	public void setValidRange(int min, int max) {
		minValidValue = min;
		maxValidValue = max;
