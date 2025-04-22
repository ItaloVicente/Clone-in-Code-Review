	private String defaultText;

	private String defaultToolTipText;

	private ImageDescriptor defaultHoverImage;

	private ImageDescriptor defaultImage;

	private ImageDescriptor defaultDisabledImage;

	private String acceleratorText;

	public LabelRetargetAction(String actionID, String text) {
		this(actionID, text, IAction.AS_UNSPECIFIED);
	}

	public LabelRetargetAction(String actionID, String text, int style) {
		super(actionID, text, style);
		this.defaultText = text;
		this.defaultToolTipText = text;
		acceleratorText = LegacyActionTools.extractAcceleratorText(text);
	}

	@Override
