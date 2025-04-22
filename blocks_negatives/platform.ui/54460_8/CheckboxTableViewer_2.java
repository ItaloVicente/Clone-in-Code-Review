        this(parent, SWT.BORDER);
    }

    /**
     * Creates a table viewer on a newly-created table control under the given parent.
     * The table control is created using the given SWT style bits, plus the
     * <code>SWT.CHECK</code> style bit.
     * The table has one column.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     * <p>
     * This also adds a <code>TableColumn</code> for the single column,
     * and sets a <code>TableLayout</code> on the table which sizes the column to fill
     * the table for its initial sizing, but does nothing on subsequent resizes.
     * </p>
     * <p>
     * If the caller just needs to show a single column with no header,
     * it is preferable to use the <code>newCheckList</code> factory method instead,
     * since SWT properly handles the initial sizing and subsequent resizes in this case.
     * </p>
     * <p>
     * If the caller adds its own columns, uses <code>Table.setHeadersVisible(true)</code>,
     * or needs to handle dynamic resizing of the table, it is recommended to
     * create the <code>Table</code> itself, specifying the <code>SWT.CHECK</code> style bit
     * (along with any other style bits needed), and use <code>new CheckboxTableViewer(Table)</code>
     * rather than this constructor.
     * </p>
     *
     * @param parent the parent control
     * @param style SWT style bits
     *
     * @deprecated use newCheckList(Composite, int) or new CheckboxTableViewer(Table)
     *   instead (see above for details)
     */
    @Deprecated
