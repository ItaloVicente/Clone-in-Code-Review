    /**
     * This viewer's list control if this viewer is instantiated with a combo control; otherwise
     * <code>null</code>.
     *
     * @see #ComboViewer(Combo)
     */
    private Combo combo;

    /**
     * This viewer's list control if this viewer is instantiated with a CCombo control; otherwise
     * <code>null</code>.
     *
     * @see #ComboViewer(CCombo)
     * @since 3.3
     */
    private CCombo ccombo;

    /**
     * Creates a combo viewer on a newly-created combo control under the given parent.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     */
    public ComboViewer(Composite parent) {
        this(parent, SWT.READ_ONLY | SWT.BORDER);
    }

    /**
     * Creates a combo viewer on a newly-created combo control under the given parent.
     * The combo control is created using the given SWT style bits.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     * @param style the SWT style bits
     */
    public ComboViewer(Composite parent, int style) {
        this(new Combo(parent, style));
    }

    /**
     * Creates a combo viewer on the given combo control.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param list the combo control
     */
    public ComboViewer(Combo list) {
        this.combo = list;
        hookControl(list);
    }

    /**
     * Creates a combo viewer on the given CCombo control.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param list the CCombo control
     * @since 3.3
     */
    public ComboViewer(CCombo list) {
        this.ccombo = list;
        hookControl(list);
    }

    @Override
