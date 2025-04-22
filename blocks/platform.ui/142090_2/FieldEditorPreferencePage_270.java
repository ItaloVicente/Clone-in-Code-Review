		implements IPropertyChangeListener {

	public static final int FLAT = 0;

	public static final int GRID = 1;

	protected static final int VERTICAL_SPACING = 10;

	protected static final int MARGIN_WIDTH = 0;

	protected static final int MARGIN_HEIGHT = 0;

	private List<FieldEditor> fields = null;

	private int style;

	private FieldEditor invalidFieldEditor = null;

	private Composite fieldEditorParent;

