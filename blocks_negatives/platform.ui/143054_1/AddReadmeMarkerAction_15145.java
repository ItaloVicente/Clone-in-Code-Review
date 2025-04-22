    private ITextEditor textEditor;

    private Object[][] customAttributes;

    private String message;


    /**
     * Creates a new action for the given text editor.
     *
     * @param editor the text editor
     * @param label the label for the action
     * @param attributes the custom attributes for this marker
     * @param message the message for the marker
     */
    public AddReadmeMarkerAction(ITextEditor editor, String label,
            Object[][] attributes, String message) {
        textEditor = editor;
        setText(label);
        this.customAttributes = attributes;
        this.message = message;
    }

    @Override
