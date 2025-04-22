    /**
     * List of legal file extension suffixes, or <code>null</code>
     * for system defaults.
     */
    private String[] extensions = null;

    /**
     * Initial path for the Browse dialog.
     */
    private File filterPath = null;

    /**
     * Indicates whether the path must be absolute;
     * <code>false</code> by default.
     */
    private boolean enforceAbsolute = false;

    /**
     * Creates a new file field editor
     */
    protected FileFieldEditor() {
    }

    /**
     * Creates a file field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public FileFieldEditor(String name, String labelText, Composite parent) {
        this(name, labelText, false, parent);
    }

    /**
     * Creates a file field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param enforceAbsolute <code>true</code> if the file path
     *  must be absolute, and <code>false</code> otherwise
     * @param parent the parent of the field editor's control
     */
    public FileFieldEditor(String name, String labelText, boolean enforceAbsolute, Composite parent) {
        this(name, labelText, enforceAbsolute, VALIDATE_ON_FOCUS_LOST, parent);
    }
    /**
     * Creates a file field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param enforceAbsolute <code>true</code> if the file path
     *  must be absolute, and <code>false</code> otherwise
     * @param validationStrategy either {@link StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE}
     *  to perform on the fly checking, or {@link StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST}
     *  (the default) to perform validation only after the text has been typed in
     * @param parent the parent of the field editor's control.
     * @since 3.4
     * @see StringButtonFieldEditor#VALIDATE_ON_KEY_STROKE
     * @see StringButtonFieldEditor#VALIDATE_ON_FOCUS_LOST
     */
    public FileFieldEditor(String name, String labelText,
            boolean enforceAbsolute, int validationStrategy, Composite parent) {
        init(name, labelText);
        this.enforceAbsolute = enforceAbsolute;
        setErrorMessage(JFaceResources
        setValidateStrategy(validationStrategy);
        createControl(parent);
    }

    @Override
