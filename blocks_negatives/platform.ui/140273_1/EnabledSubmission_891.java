    /**
     * The identifier of the part in which this context should be enabled. If
     * this value is <code>null</code>, this means it should be active in any
     * part.
     */
    private final String activePartId;

    /**
     * The shell in which this context should be enabled. If this value is
     * <code>null</code>, this means it should be active in any shell.
     */
    private final Shell activeShell;

    /**
     * The part site in which this context should be enabled. If this value is
     * <code>null</code>, this means it should be active in any part site.
     */
    private final IWorkbenchPartSite activeWorkbenchPartSite;

    /**
     * The identifier for the context that should be enabled by this
     * submissions. This value should never be <code>null</code>.
     */
    private final String contextId;

    /**
     * The cached string representation of this instance. This value is computed
     * lazily on the first call to retrieve the string representation, and the
     * cache is used for all future calls. If this value is <code>null</code>,
     * then the value has not yet been computed.
     */
    private transient String string = null;

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
     * @param contextId
     *            the identifier of the context to be enabled. Must not be
     *            <code>null</code>.
     */
    public EnabledSubmission(String activePartId, Shell activeShell,
            IWorkbenchPartSite activeWorkbenchPartSite, String contextId) {
        if (contextId == null) {
