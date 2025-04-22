    /**
     * Creates a new Web browser given its parent and a style value describing
     * its behavior and appearance.
     * <p>
     * The style value is either one of the style constants defined in the class
     * header or class <code>SWT</code> which is applicable to instances of
     * this class, or must be built by <em>bitwise OR</em>'ing together (that
     * is, using the <code>int</code> "|" operator) two or more of those
     * <code>SWT</code> style constants. The class description lists the style
     * constants that are applicable to the class. Style bits are also inherited
     * from superclasses.
     * </p>
     *
     * @param parent
     *            a composite control which will be the parent of the new
     *            instance (cannot be null)
     * @param style
     *            the style of control to construct
     */
    public BrowserViewer(Composite parent, int style) {
        super(parent, SWT.NONE);

        if ((style & LOCATION_BAR) != 0)
            showURLbar = true;

        if ((style & BUTTON_BAR) != 0)
            showToolbar = true;

        GridLayout layout = new GridLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        layout.horizontalSpacing = 0;
        layout.verticalSpacing = 0;
        layout.numColumns = 1;
        setLayout(layout);
        setLayoutData(new GridData(GridData.FILL_BOTH));
        clipboard = new Clipboard(parent.getDisplay());
