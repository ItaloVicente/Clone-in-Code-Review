	public static final int VALIDATE_ON_KEY_STROKE = 0;

	public static final int VALIDATE_ON_FOCUS_LOST = 1;

	public static int UNLIMITED = -1;

	private boolean isValid;

	protected String oldValue;

	Text textField;

	private int widthInChars = UNLIMITED;

	private int textLimit = UNLIMITED;

	private String errorMessage;

	private boolean emptyStringAllowed = true;

	private int validateStrategy = VALIDATE_ON_KEY_STROKE;

	protected StringFieldEditor() {
	}

	public StringFieldEditor(String name, String labelText, int width,
			int strategy, Composite parent) {
		init(name, labelText);
		widthInChars = width;
		setValidateStrategy(strategy);
		isValid = false;
		errorMessage = JFaceResources
				.getString("StringFieldEditor.errorMessage");//$NON-NLS-1$
		createControl(parent);
	}

	public StringFieldEditor(String name, String labelText, int width,
			Composite parent) {
		this(name, labelText, width, VALIDATE_ON_KEY_STROKE, parent);
	}

	public StringFieldEditor(String name, String labelText, Composite parent) {
		this(name, labelText, UNLIMITED, parent);
	}

	@Override
