    private int minValidValue = 0;

    private int maxValidValue = Integer.MAX_VALUE;

    private static final int DEFAULT_TEXT_LIMIT = 10;

    /**
    * Creates a new integer field editor
    */
    protected IntegerFieldEditor() {
    }

    /**
     * Creates an integer field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public IntegerFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, parent, DEFAULT_TEXT_LIMIT);
    }

    /**
     * Creates an integer field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     * @param textLimit the maximum number of characters in the text.
     */
    public IntegerFieldEditor(String name, String labelText, Composite parent,
            int textLimit) {
        init(name, labelText);
        setTextLimit(textLimit);
        setEmptyStringAllowed(false);
        setErrorMessage(JFaceResources
        createControl(parent);
    }

    /**
     * Sets the range of valid values for this field.
     *
     * @param min the minimum allowed value (inclusive)
     * @param max the maximum allowed value (inclusive)
     */
    public void setValidRange(int min, int max) {
        minValidValue = min;
        maxValidValue = max;
