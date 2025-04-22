    /**
     * The change button, or <code>null</code> if none
     * (before creation and after disposal).
     */
    private Button changeButton;

    /**
     * The text for the change button, or <code>null</code> if missing.
     */
    private String changeButtonText;

    /**
     * Creates a new string button field editor
     */
    protected StringButtonFieldEditor() {
    }

    /**
     * Creates a string button field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    protected StringButtonFieldEditor(String name, String labelText,
            Composite parent) {
        init(name, labelText);
        createControl(parent);
    }

    @Override
