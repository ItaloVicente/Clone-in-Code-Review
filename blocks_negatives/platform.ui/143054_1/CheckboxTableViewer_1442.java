    /**
     * Provides the desired state of the check boxes.
     */
    private ICheckStateProvider checkStateProvider;

    /**
     * Creates a table viewer on a newly-created table control under the given parent.
     * The table control is created using the SWT style bits:
     * <code>SWT.CHECK</code> and <code>SWT.BORDER</code>.
     * The table has one column.
     * The viewer has no input, no content provider, a default label provider,
     * no sorter, and no filters.
     * <p>
     * This is equivalent to calling <code>new CheckboxTableViewer(parent, SWT.BORDER)</code>.
     * See that constructor for more details.
     * </p>
     *
     * @param parent the parent control
     *
     * @deprecated use newCheckList(Composite, int) or new CheckboxTableViewer(Table)
     *   instead (see below for details)
     */
    @Deprecated
