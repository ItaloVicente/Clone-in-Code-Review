    /**
     * Property name constant (value <code>"field_editor_is_valid"</code>)
     * to signal a change in the validity of the value of this field editor.
     */

    /**
     * Property name constant (value <code>"field_editor_value"</code>)
     * to signal a change in the value of this field editor.
     */

    /**
     * Gap between label and control.
     */
    protected static final int HORIZONTAL_GAP = 8;

    /**
     * The preference store, or <code>null</code> if none.
     */
    private IPreferenceStore preferenceStore = null;

    /**
     * The name of the preference displayed in this field editor.
     */
    private String preferenceName;

    /**
     * Indicates whether the default value is currently displayed,
     * initially <code>false</code>.
     */
    private boolean isDefaultPresented = false;

    /**
     * The label's text.
     */
    private String labelText;

    /**
     * The label control.
     */
    private Label label;

    /**
     * Listener, or <code>null</code> if none
     */
    private IPropertyChangeListener propertyChangeListener;

    /**
     * The page containing this field editor
     */
    private DialogPage page;

    /**
     * Creates a new field editor.
     */
    protected FieldEditor() {
    }

    /**
     * Creates a new field editor.
     *
     * @param name the name of the preference this field editor works on
     * @param labelText the label text of the field editor
     * @param parent the parent of the field editor's control
     */
    protected FieldEditor(String name, String labelText, Composite parent) {
        init(name, labelText);
        createControl(parent);
    }

    /**
     * Adjusts the horizontal span of this field editor's basic controls.
     * <p>
     * Subclasses must implement this method to adjust the horizontal span
     * of controls so they appear correct in the given number of columns.
     * </p>
     * <p>
     * The number of columns will always be equal to or greater than the
     * value returned by this editor's <code>getNumberOfControls</code> method.
     *
     * @param numColumns the number of columns
     */
    protected abstract void adjustForNumColumns(int numColumns);

    /**
     * Applies a font.
     * <p>
     * The default implementation of this framework method
     * does nothing. Subclasses should override this method
     * if they want to change the font of the SWT control to
     * a value different than the standard dialog font.
     * </p>
     */
    protected void applyFont() {
    }

    /**
     * Checks if the given parent is the current parent of the
     * supplied control; throws an (unchecked) exception if they
     * are not correctly related.
     *
     * @param control the control
     * @param parent the parent control
     */
    protected void checkParent(Control control, Composite parent) {
        Assert.isTrue(control.getParent() == parent, "Different parents");//$NON-NLS-1$
    }

    /**
     * Clears the error message from the message line.
     */
    protected void clearErrorMessage() {
        if (page != null) {
