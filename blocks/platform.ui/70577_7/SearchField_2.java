	private void updateKeyBindingText() {
		updateQuickAccessTriggerSequenceFormat();
		updateText(txtQuickAcesss);
		txtQuickAcesss.requestLayout();
	}

	public String getQuickAccessTriggerSequenceFormat() {
		if (quickAccessTriggerSequenceFormat == null) {
			updateQuickAccessTriggerSequenceFormat();
		}
		return quickAccessTriggerSequenceFormat;
	}

	@Inject
	private BindingTableManager manager;
	@Inject
	private ECommandService eCommandService;

	@Inject
	IContextService contextService;

	protected void updateQuickAccessTriggerSequenceFormat() {
		TriggerSequence triggerSequence;
		ParameterizedCommand cmd = eCommandService.createCommand(QUICK_ACCESS_COMMAND_ID, null);
		ContextSet contextSet = manager.createContextSet(Arrays.asList(contextService.getDefinedContexts()));
		Binding binding = manager.getBestSequenceFor(contextSet, cmd);
		triggerSequence = binding.getTriggerSequence();
		this.quickAccessTriggerSequenceFormat = (triggerSequence == null) ? "" : triggerSequence.format(); //$NON-NLS-1$
	}

