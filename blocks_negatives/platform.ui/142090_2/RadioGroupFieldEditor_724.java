    /**
     * List of radio button entries of the form [label,value].
     */
    private String[][] labelsAndValues;

    /**
     * Number of columns into which to arrange the radio buttons.
     */
    private int numColumns;

    /**
     * Indent used for the first column of the radion button matrix.
     */
    private int indent = HORIZONTAL_GAP;

    /**
     * The current value, or <code>null</code> if none.
     */
    private String value;

    /**
     * The box of radio buttons, or <code>null</code> if none
     * (before creation and after disposal).
     */
    private Composite radioBox;

    /**
     * The radio buttons, or <code>null</code> if none
     * (before creation and after disposal).
     */
    private Button[] radioButtons;

    /**
     * Whether to use a Group control.
     */
    private boolean useGroup;

    /**
     * Creates a new radio group field editor
     */
    protected RadioGroupFieldEditor() {
    }

    /**
