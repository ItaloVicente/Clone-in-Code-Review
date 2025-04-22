            colorLabel.setBounds(-1, 0, colorSize.x, colorSize.y);
            rgbLabel.setBounds(colorSize.x + GAP - 1, ty, bounds.width
                    - colorSize.x - GAP, bounds.height);
        }
    }

    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no
     * validator.
     *
     * @param parent the parent control
     */
    public ColorCellEditor(Composite parent) {
        this(parent, SWT.NONE);
    }

    /**
     * Creates a new color cell editor parented under the given control.
     * The cell editor value is black (<code>RGB(0,0,0)</code>) initially, and has no
     * validator.
     *
     * @param parent the parent control
     * @param style the style bits
     * @since 2.1
     */
    public ColorCellEditor(Composite parent, int style) {
        super(parent, style);
        doSetValue(new RGB(0, 0, 0));
    }

    /**
     * Creates and returns the color image data for the given control
     * and RGB value. The image's size is either the control's item extent
     * or the cell editor's default extent, which is 16 pixels square.
     *
     * @param w the control
     * @param color the color
     */
    private ImageData createColorImage(Control w, RGB color) {

        GC gc = new GC(w);
        FontMetrics fm = gc.getFontMetrics();
        int size = fm.getAscent();
        gc.dispose();

        int indent = 6;
        int extent = DEFAULT_EXTENT;
        if (w instanceof Table) {
