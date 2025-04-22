	private final IContextManager contextManager;

	private final boolean definedContextIdsChanged;

	private final boolean enabledContextIdsChanged;

	private final Set previouslyDefinedContextIds;

	private final Set previouslyEnabledContextIds;

	public ContextManagerEvent(IContextManager contextManager, boolean definedContextIdsChanged,
			boolean enabledContextIdsChanged, Set previouslyDefinedContextIds, Set previouslyEnabledContextIds) {
		if (contextManager == null) {
