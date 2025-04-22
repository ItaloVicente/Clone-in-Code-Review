    private String defaultText;

    private String defaultToolTipText;

    private ImageDescriptor defaultHoverImage;

    private ImageDescriptor defaultImage;

    private ImageDescriptor defaultDisabledImage;

    private String acceleratorText;

    /**
     * Constructs a LabelRetargetAction with the given action id and text.
     *
     * @param actionID the retargetable action id
     * @param text the action's text, or <code>null</code> if there is no text
     */
    public LabelRetargetAction(String actionID, String text) {
        this(actionID, text, IAction.AS_UNSPECIFIED);
    }

    /**
     * Constructs a RetargetAction with the given action id, text and style.
     *
     * @param actionID the retargetable action id
     * @param text the action's text, or <code>null</code> if there is no text
     * @param style one of <code>AS_PUSH_BUTTON</code>, <code>AS_CHECK_BOX</code>,
     * 		<code>AS_DROP_DOWN_MENU</code>, <code>AS_RADIO_BUTTON</code>, and
     * 		<code>AS_UNSPECIFIED</code>.
     * @since 3.0
     */
    public LabelRetargetAction(String actionID, String text, int style) {
        super(actionID, text, style);
        this.defaultText = text;
        this.defaultToolTipText = text;
        acceleratorText = LegacyActionTools.extractAcceleratorText(text);
    }

    /**
     * The action handler has changed.  Update self.
     */
    @Override
