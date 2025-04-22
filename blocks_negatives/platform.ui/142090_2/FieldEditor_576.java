    }

    /**
     * Returns the number of pixels corresponding to the
     * given number of horizontal dialog units.
     * <p>
     * Clients may call this framework method, but should not override it.
     * </p>
     *
     * @param control the control being sized
     * @param dlus the number of horizontal dialog units
     * @return the number of pixels
     */
    protected int convertHorizontalDLUsToPixels(Control control, int dlus) {
        GC gc = new GC(control);
        gc.setFont(control.getFont());
        int averageWidth = gc.getFontMetrics().getAverageCharWidth();
        gc.dispose();

        double horizontalDialogUnitSize = averageWidth * 0.25;

        return (int) Math.round(dlus * horizontalDialogUnitSize);
    }

    /**
     * Returns the number of pixels corresponding to the
     * given number of vertical dialog units.
     * <p>
     * Clients may call this framework method, but should not override it.
     * </p>
     *
     * @param control the control being sized
     * @param dlus the number of vertical dialog units
     * @return the number of pixels
     */
    protected int convertVerticalDLUsToPixels(Control control, int dlus) {
        GC gc = new GC(control);
        gc.setFont(control.getFont());
        int height = gc.getFontMetrics().getHeight();
        gc.dispose();

        double verticalDialogUnitSize = height * 0.125;

        return (int) Math.round(dlus * verticalDialogUnitSize);
    }

    /**
     * Creates this field editor's main control containing all of its
     * basic controls.
     *
     * @param parent the parent control
     */
    protected void createControl(Composite parent) {
        GridLayout layout = new GridLayout();
        layout.numColumns = getNumberOfControls();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        layout.horizontalSpacing = HORIZONTAL_GAP;
        parent.setLayout(layout);
        doFillIntoGrid(parent, layout.numColumns);
    }

    /**
     * Disposes the SWT resources used by this field editor.
     */
    public void dispose() {
    }

    /**
     * Fills this field editor's basic controls into the given parent.
     * <p>
     * Subclasses must implement this method to create the controls
     * for this field editor.
     * </p>
     * <p>
     * Note this method may be called by the constructor, so it must not access
     * fields on the receiver object because they will not be fully initialized.
     * </p>
     *
     * @param parent the composite used as a parent for the basic controls;
     *	the parent's layout must be a <code>GridLayout</code>
     * @param numColumns the number of columns
     */
    protected abstract void doFillIntoGrid(Composite parent, int numColumns);

    /**
     * Initializes this field editor with the preference value from
     * the preference store.
     * <p>
     * Subclasses must implement this method to properly initialize
     * the field editor.
     * </p>
     */
    protected abstract void doLoad();

    /**
     * Initializes this field editor with the default preference value from
     * the preference store.
     * <p>
     * Subclasses must implement this method to properly initialize
     * the field editor.
     * </p>
     */
    protected abstract void doLoadDefault();

    /**
     * Stores the preference value from this field editor into
     * the preference store.
     * <p>
     * Subclasses must implement this method to save the entered value
     * into the preference store.
     * </p>
     */
    protected abstract void doStore();

    /**
     * Fills this field editor's basic controls into the given parent.
     *
     * @param parent the composite used as a parent for the basic controls;
     *	the parent's layout must be a <code>GridLayout</code>
     * @param numColumns the number of columns
     */
    public void fillIntoGrid(Composite parent, int numColumns) {
        Assert.isTrue(numColumns >= getNumberOfControls());
        Assert.isTrue(parent.getLayout() instanceof GridLayout);
        doFillIntoGrid(parent, numColumns);
    }

    /**
     * Informs this field editor's listener, if it has one, about a change to
     * one of this field editor's boolean-valued properties. Does nothing
     * if the old and new values are the same.
     *
     * @param property the field editor property name,
     *   such as <code>VALUE</code> or <code>IS_VALID</code>
     * @param oldValue the old value
     * @param newValue the new value
     */
    protected void fireStateChanged(String property, boolean oldValue,
            boolean newValue) {
        if (oldValue == newValue) {
