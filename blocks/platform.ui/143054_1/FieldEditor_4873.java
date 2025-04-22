	public static final String IS_VALID = "field_editor_is_valid";//$NON-NLS-1$

	public static final String VALUE = "field_editor_value";//$NON-NLS-1$

	protected static final int HORIZONTAL_GAP = 8;

	private IPreferenceStore preferenceStore = null;

	private String preferenceName;

	private boolean isDefaultPresented = false;

	private String labelText;

	private Label label;

	private IPropertyChangeListener propertyChangeListener;

	private DialogPage page;

	protected FieldEditor() {
	}

	protected FieldEditor(String name, String labelText, Composite parent) {
		init(name, labelText);
		createControl(parent);
	}

	protected abstract void adjustForNumColumns(int numColumns);

	protected void applyFont() {
	}

	protected void checkParent(Control control, Composite parent) {
		Assert.isTrue(control.getParent() == parent, "Different parents");//$NON-NLS-1$
	}

	protected void clearErrorMessage() {
		if (page != null) {
