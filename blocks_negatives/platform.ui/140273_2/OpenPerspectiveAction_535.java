    /**
     * Constructs a new instance of <code>OpenPerspectiveAction</code>
     *
     * @param window
     *            The workbench window in which this action is created; should
     *            not be <code>null</code>.
     * @param descriptor
     *            The descriptor for the perspective that this action should
     *            open; must not be <code>null</code>.
     * @param callback
     *            The perspective menu who will handle the actual execution of
     *            this action; should not be <code>null</code>.
     */
    public OpenPerspectiveAction(final IWorkbenchWindow window,
            final IPerspectiveDescriptor descriptor,
            final PerspectiveMenu callback) {
        super(Util.ZERO_LENGTH_STRING);
