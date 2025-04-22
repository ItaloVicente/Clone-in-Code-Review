    /**
     * The context manager which has changed. This value is never
     * <code>null</code>.
     */
    private final IContextManager contextManager;

    /**
     * Whether the set of defined contexts has changed.
     */
    private final boolean definedContextIdsChanged;

    /**
     * Whether the set of enabled contexts has changed.
     */
    private final boolean enabledContextIdsChanged;

    /**
     * The set of context identifiers (strings) that were defined before the
     * change occurred. If the defined contexts did not changed, then this value
     * is <code>null</code>.
     */
    private final Set previouslyDefinedContextIds;

    /**
     * The set of context identifiers (strings) that were enabled before the
     * change occurred. If the enabled contexts did not changed, then this value
     * is <code>null</code>.
     */
    private final Set previouslyEnabledContextIds;

    /**
     * Creates a new instance of this class.
     *
     * @param contextManager
     *            the instance of the interface that changed.
     * @param definedContextIdsChanged
     *            true, iff the definedContextIds property changed.
     * @param enabledContextIdsChanged
     *            true, iff the enabledContextIds property changed.
     * @param previouslyDefinedContextIds
     *            the set of identifiers to previously defined contexts. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if definedContextIdsChanged is
     *            <code>false</code> and must not be null if
     *            definedContextIdsChanged is <code>true</code>.
     * @param previouslyEnabledContextIds
     *            the set of identifiers to previously enabled contexts. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if enabledContextIdsChanged is
     *            <code>false</code> and must not be null if
     *            enabledContextIdsChanged is <code>true</code>.
     */
    public ContextManagerEvent(IContextManager contextManager,
            boolean definedContextIdsChanged, boolean enabledContextIdsChanged,
            Set previouslyDefinedContextIds, Set previouslyEnabledContextIds) {
        if (contextManager == null) {
