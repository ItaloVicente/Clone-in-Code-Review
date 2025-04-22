	public static final int T_POPUP = 0x1;

	public static final int T_VIEW = 0x2;

	public static final int T_WORKBENCH = 0x3;

	public static final int T_EDITOR = 0x4;

	public static final int T_WORKBENCH_PULLDOWN = 0x5;

	public static final String STYLE_PUSH = "push"; //$NON-NLS-1$

	public static final String STYLE_RADIO = "radio"; //$NON-NLS-1$

	public static final String STYLE_TOGGLE = "toggle"; //$NON-NLS-1$

	public static final String STYLE_PULLDOWN = "pulldown"; //$NON-NLS-1$

	public ActionDescriptor(IConfigurationElement actionElement, int targetType) {
		this(actionElement, targetType, null);
	}

	public ActionDescriptor(IConfigurationElement actionElement, int targetType, Object target) {
		id = actionElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
