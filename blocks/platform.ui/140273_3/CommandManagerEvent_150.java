	private final boolean activeContextIdsChanged;

	private final boolean activeKeyConfigurationIdChanged;

	private final boolean activeLocaleChanged;

	private final boolean activePlatformChanged;

	private final ICommandManager commandManager;

	private final boolean definedCategoryIdsChanged;

	private final boolean definedCommandIdsChanged;

	private final boolean definedKeyConfigurationIdsChanged;

	private final Set previouslyDefinedCategoryIds;

	private final Set previouslyDefinedCommandIds;

	private final Set previouslyDefinedKeyConfigurationIds;

	public CommandManagerEvent(ICommandManager commandManager, boolean activeContextIdsChanged,
			boolean activeKeyConfigurationIdChanged, boolean activeLocaleChanged, boolean activePlatformChanged,
			boolean definedCategoryIdsChanged, boolean definedCommandIdsChanged,
			boolean definedKeyConfigurationIdsChanged, Set previouslyDefinedCategoryIds,
			Set previouslyDefinedCommandIds, Set previouslyDefinedKeyConfigurationIds) {
		if (commandManager == null) {
