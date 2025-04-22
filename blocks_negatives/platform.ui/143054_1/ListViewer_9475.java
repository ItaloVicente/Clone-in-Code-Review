    /**
     * This viewer's list control.
     */
    private org.eclipse.swt.widgets.List list;

    /**
     * Creates a list viewer on a newly-created list control under the given parent.
     * The list control is created using the SWT style bits <code>MULTI, H_SCROLL, V_SCROLL,</code> and <code>BORDER</code>.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     */
    public ListViewer(Composite parent) {
        this(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
    }

    /**
     * Creates a list viewer on a newly-created list control under the given parent.
     * The list control is created using the given SWT style bits.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param parent the parent control
     * @param style the SWT style bits
     */
    public ListViewer(Composite parent, int style) {
        this(new org.eclipse.swt.widgets.List(parent, style));
    }

    /**
     * Creates a list viewer on the given list control.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param list the list control
     */
    public ListViewer(org.eclipse.swt.widgets.List list) {
        this.list = list;
        hookControl(list);
    }

    @Override
