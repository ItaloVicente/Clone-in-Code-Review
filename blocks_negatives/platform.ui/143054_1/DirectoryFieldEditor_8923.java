    /**
     * Creates a directory field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    public DirectoryFieldEditor(String name, String labelText, Composite parent) {
        init(name, labelText);
        setErrorMessage(JFaceResources
        setValidateStrategy(VALIDATE_ON_FOCUS_LOST);
        createControl(parent);
    }
