    /**
     * Whether the set of active contexts has changed.
     */
    private final boolean activeContextIdsChanged;

    /**
     * Whether the active key configuration has changed.
     */
    private final boolean activeKeyConfigurationIdChanged;

    /**
     * Whether the locale has changed.
     */
    private final boolean activeLocaleChanged;

    /**
     * Whether the platform has changed.
     */
    private final boolean activePlatformChanged;

    /**
     * Whether the command manager has changed.
     */
    private final ICommandManager commandManager;

    /**
     * Whether the list of defined categories has changed.
     */
    private final boolean definedCategoryIdsChanged;

    /**
     * Whether the list of defined commands has changed.
     */
    private final boolean definedCommandIdsChanged;

    /**
     * Whether the list of defined key configurations has changed.
     */
    private final boolean definedKeyConfigurationIdsChanged;

    /**
     * The set of the defined categories before the change occurred.  This is a
     * set of strings (category identifiers).
     */
    private final Set previouslyDefinedCategoryIds;

    /**
     * The set of the defined commands before the change occurred.  This is a
     * set of strings (command identifiers).
     */
    private final Set previouslyDefinedCommandIds;

    /**
     * The set of the defined key configurations before the change occurred.
     * This is a set of strings (key configuration identifiers).
     */
    private final Set previouslyDefinedKeyConfigurationIds;

    /**
     * Creates a new instance of this class.
     *
     * @param commandManager
     *            the instance of the interface that changed.
     * @param activeContextIdsChanged
     *            true, iff the activeContextIdsChanged property changed.
     * @param activeKeyConfigurationIdChanged
     *            true, iff the activeKeyConfigurationIdChanged property
     *            changed.
     * @param activeLocaleChanged
     *            true, iff the activeLocaleChanged property changed.
     * @param activePlatformChanged
     *            true, iff the activePlatformChanged property changed.
     * @param definedCategoryIdsChanged
     *            true, iff the definedCategoryIdsChanged property changed.
     * @param definedCommandIdsChanged
     *            true, iff the definedCommandIdsChanged property changed.
     * @param definedKeyConfigurationIdsChanged
     *            true, iff the definedKeyConfigurationIdsChanged property
     *            changed.
     * @param previouslyDefinedCategoryIds
     *            the set of identifiers to previously defined categories. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if definedCategoryIdsChanged is
     *            <code>false</code> and must not be null if
     *            definedCategoryIdsChanged is <code>true</code>.
     * @param previouslyDefinedCommandIds
     *            the set of identifiers to previously defined commands. This
     *            set may be empty. If this set is not empty, it must only
     *            contain instances of <code>String</code>. This set must be
     *            <code>null</code> if definedCommandIdsChanged is
     *            <code>false</code> and must not be null if
     *            definedContextIdsChanged is <code>true</code>.
     * @param previouslyDefinedKeyConfigurationIds
     *            the set of identifiers to previously defined key
     *            configurations. This set may be empty. If this set is not
     *            empty, it must only contain instances of <code>String</code>.
     *            This set must be <code>null</code> if
     *            definedKeyConfigurationIdsChanged is <code>false</code> and
     *            must not be null if definedKeyConfigurationIdsChanged is
     *            <code>true</code>.
     */
    public CommandManagerEvent(ICommandManager commandManager,
            boolean activeContextIdsChanged,
            boolean activeKeyConfigurationIdChanged,
            boolean activeLocaleChanged, boolean activePlatformChanged,
            boolean definedCategoryIdsChanged,
            boolean definedCommandIdsChanged,
            boolean definedKeyConfigurationIdsChanged,
            Set previouslyDefinedCategoryIds, Set previouslyDefinedCommandIds,
            Set previouslyDefinedKeyConfigurationIds) {
        if (commandManager == null) {
