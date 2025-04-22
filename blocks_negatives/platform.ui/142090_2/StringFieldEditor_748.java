    /**
     * Validation strategy constant (value <code>0</code>) indicating that
     * the editor should perform validation after every key stroke.
     *
     * @see #setValidateStrategy
     */
    public static final int VALIDATE_ON_KEY_STROKE = 0;

    /**
     * Validation strategy constant (value <code>1</code>) indicating that
     * the editor should perform validation only when the text widget
     * loses focus.
     *
     * @see #setValidateStrategy
     */
    public static final int VALIDATE_ON_FOCUS_LOST = 1;

    /**
     * Text limit constant (value <code>-1</code>) indicating unlimited
     * text limit and width.
     */
    public static int UNLIMITED = -1;

    /**
     * Cached valid state.
     */
    private boolean isValid;

    /**
     * Old text value.
     * @since 3.4 this field is protected.
     */
    protected String oldValue;

    /**
     * The text field, or <code>null</code> if none.
     */
    Text textField;

    /**
     * Width of text field in characters; initially unlimited.
     */
    private int widthInChars = UNLIMITED;

    /**
     * Text limit of text field in characters; initially unlimited.
     */
    private int textLimit = UNLIMITED;

    /**
     * The error message, or <code>null</code> if none.
     */
    private String errorMessage;

    /**
     * Indicates whether the empty string is legal;
     * <code>true</code> by default.
     */
    private boolean emptyStringAllowed = true;

    /**
     * The validation strategy;
     * <code>VALIDATE_ON_KEY_STROKE</code> by default.
     */
    private int validateStrategy = VALIDATE_ON_KEY_STROKE;

    /**
     * Creates a new string field editor
     */
    protected StringFieldEditor() {
    }

    /**
     * Creates a string field editor.
     * Use the method <code>setTextLimit</code> to limit the text.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param width the width of the text input field in characters,
     *  or <code>UNLIMITED</code> for no limit
     * @param strategy either <code>VALIDATE_ON_KEY_STROKE</code> to perform
     *  on the fly checking (the default), or <code>VALIDATE_ON_FOCUS_LOST</code> to
     *  perform validation only after the text has been typed in
     * @param parent the parent of the field editor's control
     * @since 2.0
     */
    public StringFieldEditor(String name, String labelText, int width,
            int strategy, Composite parent) {
        init(name, labelText);
        widthInChars = width;
        setValidateStrategy(strategy);
        isValid = false;
        errorMessage = JFaceResources
        createControl(parent);
    }

    /**
     * Creates a string field editor.
     * Use the method <code>setTextLimit</code> to limit the text.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param width the width of the text input field in characters,
     *  or <code>UNLIMITED</code> for no limit
     * @param parent the parent of the field editor's control
     */
    public StringFieldEditor(String name, String labelText, int width,
            Composite parent) {
        this(name, labelText, width, VALIDATE_ON_KEY_STROKE, parent);
    }

    /**
     * Creates a string field editor of unlimited width.
     * Use the method <code>setTextLimit</code> to limit the text.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public StringFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, UNLIMITED, parent);
    }

    @Override
