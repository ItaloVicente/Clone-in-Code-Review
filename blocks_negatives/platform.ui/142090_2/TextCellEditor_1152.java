    /**
     * The text control; initially <code>null</code>.
     */
    protected Text text;

    private ModifyListener modifyListener;

    /**
     * State information for updating action enablement
     */
    private boolean isSelection = false;

    private boolean isDeleteable = false;

    private boolean isSelectable = false;

    /**
     * Default TextCellEditor style
     * specify no borders on text widget as cell outline in table already
     * provides the look of a border.
     */
    private static final int defaultStyle = SWT.SINGLE;

    /**
     * Creates a new text string cell editor with no control
     * The cell editor value is the string itself, which is initially the empty
     * string. Initially, the cell editor has no cell validator.
     *
     * @since 2.1
     */
    public TextCellEditor() {
        setStyle(defaultStyle);
    }

    /**
     * Creates a new text string cell editor parented under the given control.
     * The cell editor value is the string itself, which is initially the empty string.
     * Initially, the cell editor has no cell validator.
     *
     * @param parent the parent control
     */
    public TextCellEditor(Composite parent) {
        this(parent, defaultStyle);
    }

    /**
     * Creates a new text string cell editor parented under the given control.
     * The cell editor value is the string itself, which is initially the empty string.
     * Initially, the cell editor has no cell validator.
     *
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public TextCellEditor(Composite parent, int style) {
        super(parent, style);
    }

    /**
     * Checks to see if the "deletable" state (can delete/
     * nothing to delete) has changed and if so fire an
     * enablement changed notification.
     */
    private void checkDeleteable() {
        boolean oldIsDeleteable = isDeleteable;
        isDeleteable = isDeleteEnabled();
        if (oldIsDeleteable != isDeleteable) {
            fireEnablementChanged(DELETE);
        }
    }

    /**
     * Checks to see if the "selectable" state (can select)
     * has changed and if so fire an enablement changed notification.
     */
    private void checkSelectable() {
        boolean oldIsSelectable = isSelectable;
        isSelectable = isSelectAllEnabled();
        if (oldIsSelectable != isSelectable) {
            fireEnablementChanged(SELECT_ALL);
        }
    }

    /**
     * Checks to see if the selection state (selection /
     * no selection) has changed and if so fire an
     * enablement changed notification.
     */
    private void checkSelection() {
        boolean oldIsSelection = isSelection;
        isSelection = text.getSelectionCount() > 0;
        if (oldIsSelection != isSelection) {
            fireEnablementChanged(COPY);
            fireEnablementChanged(CUT);
        }
    }

    @Override
