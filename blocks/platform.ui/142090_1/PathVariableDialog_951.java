	private Button okButton;

	private Label variableNameLabel;

	private Label variableValueLabel;

	private Label variableResolvedValueLabel;

	private Text variableNameField;

	private Text variableValueField;

	private Label variableResolvedValueField;

	private Button fileButton;

	private Button folderButton;

	private Button variableButton;
	private int type;

	private int variableType;

	private String variableName;

	private String variableValue;

	private String originalName;

	private int operationMode = 0;

	private IPathVariableManager pathVariableManager;

	private Set namesInUse;

	private int validationStatus;

	private String validationMessage;

	private boolean nameEntered = false;

	private boolean locationEntered = false;

	final private String standardMessage;

	public static final int NEW_VARIABLE = 1;

	public static final int EXISTING_VARIABLE = 2;

	public static final int EDIT_LINK_LOCATION = 3;

	private IResource currentResource = null;

	public PathVariableDialog(Shell parentShell, int type, int variableType,
			IPathVariableManager pathVariableManager, Set namesInUse) {
		super(parentShell);
		this.type = type;
		this.operationMode = type;
		this.variableName = ""; //$NON-NLS-1$
		this.variableValue = ""; //$NON-NLS-1$
		this.variableType = variableType;
		this.pathVariableManager = pathVariableManager;
		this.namesInUse = namesInUse;

		if (operationMode == NEW_VARIABLE)
			this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_newVariable;
		else if (operationMode == EXISTING_VARIABLE)
			this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_existingVariable;
		else
			this.standardMessage = IDEWorkbenchMessages.PathVariableDialog_message_editLocation;
	}

	@Override
