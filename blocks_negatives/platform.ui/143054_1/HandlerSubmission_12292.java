    /**
     * The part identifier for the part that should be active before this
     * submission can be considered.  This value can be <code>null</code>, which
     * indicates that it should match any part.
     */
    private final String activePartId;

    /**
     * The shell that must be active before this submission can be considered.
     * This value can be <code>null</code>, which indicates that it should match
     * any shell.
     */
    private final Shell activeShell;

    /**
     * The workbench site that must be active before this submission can be
     * considered.  This value can be <code>null</code>, which indicates that it
     * should match an workbench part site.
     */
    private final IWorkbenchPartSite activeWorkbenchPartSite;

    /**
     * The identifier for the command which the submitted handler handles.  This
     * value cannot be <code>null</code>.
     */
    private final String commandId;

    /**
     * The handler being submitted.  This value cannot be <code>null</code>.
     */
    private final IHandler handler;

    /**
     * The priority for this submission.  In the event of all other factors
     * being equal, the priority will be considered in an attempt to resolve
     * conflicts.  This value cannot be <code>null</code>.
     */
    private final Priority priority;

    /**
     * A lazily computed cache of the string representation of this submission.
     * This value is computed once; before it is computed, it is
     * <code>null</code>.
     */
    private transient String string;

    /**
     * Creates a new instance of this class.
     *
     * @param activePartId
     *            the identifier of the part that must be active for this
     *            request to be considered. May be <code>null</code>.
     * @param activeShell
     *            the shell that must be active for this request to be
     *            considered. May be <code>null</code>.
     * @param activeWorkbenchPartSite
     *            the workbench part site of the part that must be active for
     *            this request to be considered. May be <code>null</code>.
     * @param commandId
     *            the identifier of the command to be handled. Must not be
     *            <code>null</code>.
     * @param handler
     *            the handler. Must not be <code>null</code>.
     * @param priority
     *            the priority. Must not be <code>null</code>.
     */
