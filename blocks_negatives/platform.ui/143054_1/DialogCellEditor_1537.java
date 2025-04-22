            Point contentsSize = contents.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                    force);
            Point buttonSize = button.computeSize(SWT.DEFAULT, SWT.DEFAULT,
                    force);
            Point result = new Point(buttonSize.x, Math.max(contentsSize.y,
                    buttonSize.y));
            return result;
        }
    }

    /**
     * Default DialogCellEditor style
     */
    private static final int defaultStyle = SWT.NONE;

    /**
     * Creates a new dialog cell editor with no control
     * @since 2.1
     */
    public DialogCellEditor() {
        setStyle(defaultStyle);
    }

    /**
     * Creates a new dialog cell editor parented under the given control.
     * The cell editor value is <code>null</code> initially, and has no
     * validator.
     *
     * @param parent the parent control
     */
    protected DialogCellEditor(Composite parent) {
        this(parent, defaultStyle);
    }

    /**
     * Creates a new dialog cell editor parented under the given control.
     * The cell editor value is <code>null</code> initially, and has no
     * validator.
     *
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    protected DialogCellEditor(Composite parent, int style) {
        super(parent, style);
    }

    /**
     * Creates the button for this cell editor under the given parent control.
     * <p>
     * The default implementation of this framework method creates the button
     * display on the right hand side of the dialog cell editor. Subclasses
     * may extend or reimplement.
     * </p>
     *
     * @param parent the parent control
     * @return the new button control
     */
    protected Button createButton(Composite parent) {
        Button result = new Button(parent, SWT.DOWN);
        return result;
    }

    /**
     * Creates the controls used to show the value of this cell editor.
     * <p>
     * The default implementation of this framework method creates
     * a label widget, using the same font and background color as the parent control.
     * </p>
     * <p>
     * Subclasses may reimplement.  If you reimplement this method, you
     * should also reimplement <code>updateContents</code>.
     * </p>
     *
     * @param cell the control for this cell editor
     * @return the underlying control
     */
    protected Control createContents(Composite cell) {
        defaultLabel = new Label(cell, SWT.LEFT);
        defaultLabel.setFont(cell.getFont());
        defaultLabel.setBackground(cell.getBackground());
        return defaultLabel;
    }

    @Override
