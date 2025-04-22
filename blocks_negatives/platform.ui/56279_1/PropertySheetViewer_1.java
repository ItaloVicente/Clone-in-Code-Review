    /**
     * Creates a property sheet viewer on a newly-created tree control
     * under the given parent. The viewer has no input, and no root entry.
     *
     * @param parent
     *            the parent control
     */
    public PropertySheetViewer(Composite parent) {
        tree = new Tree(parent, SWT.FULL_SELECTION | SWT.SINGLE
                | SWT.HIDE_SELECTION);
