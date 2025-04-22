
	@Inject
	private BindingTableManager manager;
	@Inject
	private ECommandService eCommandService;
	@Inject
	private IContextService contextService;

	protected void updateQuickAccessTriggerSequence() {
		triggerSequence = bindingService.getBestActiveBindingFor(QUICK_ACCESS_COMMAND_ID);
		if (triggerSequence == null) {
			ParameterizedCommand cmd = eCommandService.createCommand(QUICK_ACCESS_COMMAND_ID, null);
			ContextSet contextSet = manager.createContextSet(Arrays.asList(contextService.getDefinedContexts()));
			Binding binding = manager.getBestSequenceFor(contextSet, cmd);
			triggerSequence = (binding == null) ? null : binding.getTriggerSequence();
		}
	}

