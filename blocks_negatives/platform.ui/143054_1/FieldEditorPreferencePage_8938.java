        implements IPropertyChangeListener {

    /**
     * Layout constant (value <code>0</code>) indicating that
     * each field editor is handled as a single component.
     */
    public static final int FLAT = 0;

    /**
     * Layout constant (value <code>1</code>) indicating that
     * the field editors' basic controls are put into a grid layout.
     */
    public static final int GRID = 1;

    /**
     * The vertical spacing used by layout styles <code>FLAT</code>
     * and <code>GRID</code>.
     */
    protected static final int VERTICAL_SPACING = 10;

    /**
     * The margin width used by layout styles <code>FLAT</code>
     * and <code>GRID</code>.
     */
    protected static final int MARGIN_WIDTH = 0;

    /**
     * The margin height used by layout styles <code>FLAT</code>
     * and <code>GRID</code>.
     */
    protected static final int MARGIN_HEIGHT = 0;

    /**
     * The field editors, or <code>null</code> if not created yet.
     */
    private List<FieldEditor> fields = null;

    /**
     * The layout style; either <code>FLAT</code> or <code>GRID</code>.
     */
    private int style;

    /**
     * The first invalid field editor, or <code>null</code>
     * if all field editors are valid.
     */
    private FieldEditor invalidFieldEditor = null;

    /**
     * The parent composite for field editors
     */
    private Composite fieldEditorParent;

    /**
	 * Create a new instance of the reciever.
