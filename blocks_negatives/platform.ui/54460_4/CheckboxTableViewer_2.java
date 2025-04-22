        this(createTable(parent, style));
    }

    /**
     * Creates a table viewer on a newly-created table control under the given parent.
     * The table control is created using the given SWT style bits, plus the
     * <code>SWT.CHECK</code> style bit.
     * The table shows its contents in a single column, with no header.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     * <p>
     * No <code>TableColumn</code> is added. SWT does not require a
     * <code>TableColumn</code> if showing only a single column with no header.
     * SWT correctly handles the initial sizing and subsequent resizes in this case.
     *
     * @param parent the parent control
     * @param style SWT style bits
     *
     * @since 2.0
     * @return CheckboxTableViewer
     */
    public static CheckboxTableViewer newCheckList(Composite parent, int style) {
        Table table = new Table(parent, SWT.CHECK | style);
        return new CheckboxTableViewer(table);
    }

    /**
     * Creates a table viewer on the given table control.
     * The <code>SWT.CHECK</code> style bit must be set on the given table control.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     *
     * @param table the table control
     */
    public CheckboxTableViewer(Table table) {
        super(table);
    }

    @Override
