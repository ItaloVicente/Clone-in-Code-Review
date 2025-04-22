        IStatusLineManager {

    /**
     * Identifier of group marker used to position contributions at the beginning
     * of the status line.
     *
     * @since 3.0
     */

    /**
     * Identifier of group marker used to position contributions in the middle
     * of the status line.
     *
     * @since 3.0
     */

    /**
     * Identifier of group marker used to position contributions at the end
     * of the status line.
     *
     * @since 3.0
     */

    /**
     * The status line control; <code>null</code> before
     * creation and after disposal.
     */
    private Composite statusLine = null;

    /**
     * Creates a new status line manager.
     * Use the <code>createControl</code> method to create the
     * status line control.
     */
    public StatusLineManager() {
    	add(new GroupMarker(BEGIN_GROUP));
        add(new GroupMarker(MIDDLE_GROUP));
        add(new GroupMarker(END_GROUP));
    }

    /**
     * Creates and returns this manager's status line control.
     * Does not create a new control if one already exists.
     * <p>
     * Note: Since 3.0 the return type is <code>Control</code>.  Before 3.0, the return type was
     *   the package-private class <code>StatusLine</code>.
     * </p>
     *
     * @param parent the parent control
     * @return the status line control
     */
    public Control createControl(Composite parent) {
        return createControl(parent, SWT.NONE);
    }

    /**
     * Creates and returns this manager's status line control.
     * Does not create a new control if one already exists.
     *
     * @param parent the parent control
     * @param style the style for the control
     * @return the status line control
     * @since 3.0
     */
    public Control createControl(Composite parent, int style) {
        if (!statusLineExist() && parent != null) {
            statusLine = new StatusLine(parent, style);
            update(false);
        }
        return statusLine;
    }

    /**
     * Disposes of this status line manager and frees all allocated SWT resources.
     * Notifies all contribution items of the dispose. Note that this method does
     * not clean up references between this status line manager and its associated
     * contribution items. Use <code>removeAll</code> for that purpose.
     */
    public void dispose() {
        if (statusLineExist()) {
